package com.example.auth.ui.login

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth.R
import com.example.authdomain.model.Auth
import com.example.authdomain.model.ValidationError
import com.example.authdomain.usecase.LoginUseCase
import com.example.authdomain.usecase.SaveCredentialUseCase
import com.example.core.model.ErrorCause
import com.example.core.model.MessageState
import com.example.core.model.Result
import com.example.core.utils.logger.Logger
import com.example.core.utils.login.LoginStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCase: LoginUseCase,
    private val saveCredentialUseCase: SaveCredentialUseCase,
    @Named("console_logger") private val logger: Logger,
    private val loginStatus: LoginStatus
) : ViewModel() {

    var username by mutableStateOf("")
    var password by mutableStateOf("")

    private var _snackBarMessage = Channel<MessageState>()
    val snackBarMessage = _snackBarMessage.receiveAsFlow()

    private var _loadingDialog = mutableStateOf(false)
    val loadingDialog: State<Boolean> = _loadingDialog

    fun login() {
        changeLoadingDialogState(state = true)
        viewModelScope.launch(Dispatchers.IO) {
            val result = useCase(auth = Auth(email = username, password = password))
            changeLoadingDialogState(state = false)
            if (result is Result.Success) {
                logger.log("LoginViewModel", "success")
                saveCredentialUseCase(result.data)
                loginStatus.checkIsUserLoggedIn(result.data)
                _snackBarMessage.send(MessageState(messageId = R.string.logged_in_successfully))
            } else if (result is Result.Error) {
                if (result.error.errorCause == ErrorCause.VALIDATION) {
                    if (result.error.errorResponse == ValidationError.EMAIL.toString()) {
                        logger.log("LoginViewModel", "username_validation_error_message")
                        _snackBarMessage.send(MessageState(messageId = R.string.email_validation_error_message))
                    } else {
                        logger.log("LoginViewModel", "password_validation_error_message")
                        _snackBarMessage.send(MessageState(messageId = R.string.password_validation_error_message))
                    }
                } else if (result.error.errorCause == ErrorCause.API) {
                    if (result.error.errorCode == 500) {
                        logger.log("LoginViewModel", "error_in_server")
                        _snackBarMessage.send(MessageState(messageId = R.string.error_in_server))
                    } else {
                        logger.log(
                            "LoginViewModel", "server error result: " + result.error.errorResponse
                        )
                        _snackBarMessage.send(MessageState(message = result.error.errorResponse))
                    }
                } else {
                    _snackBarMessage.send(MessageState(messageId = R.string.error_in_server))
                }
            }
        }
    }

    private fun changeLoadingDialogState(state: Boolean) {
        _loadingDialog.value = state
    }
}