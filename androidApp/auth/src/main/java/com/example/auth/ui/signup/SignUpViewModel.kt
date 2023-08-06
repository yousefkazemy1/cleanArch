package com.example.auth.ui.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth.R
import com.example.authdomain.model.Auth
import com.example.authdomain.model.ValidationError
import com.example.authdomain.usecase.SignUpUseCase
import com.example.core.model.ErrorCause
import com.example.core.model.MessageState
import com.example.core.model.Result
import com.example.core.utils.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    @Named("console_logger") private val logger: Logger,
) : ViewModel() {

    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var name by mutableStateOf("")

    private var _snackBarMessage = Channel<MessageState>()
    val snackBarMessage = _snackBarMessage.receiveAsFlow()

    fun register() {
        viewModelScope.launch(Dispatchers.IO) {
            signUp()
        }
    }

    private suspend fun signUp() {
        val result = signUpUseCase.invoke(
            auth = Auth(
                email = email, password = password, name = name
            )
        )
        if (result is Result.Success) {
            _snackBarMessage.send(MessageState(messageId = R.string.signed_up_successfully))
        } else if (result is Result.Error) {
            if (result.error.errorCause == ErrorCause.VALIDATION) {
                when (result.error.errorResponse) {
                    ValidationError.EMAIL.toString() -> {
                        logger.log("SignUpViewModel", "username_validation_error_message")
                        _snackBarMessage.send(MessageState(messageId = R.string.email_validation_error_message))
                    }
                    ValidationError.PASSWORD.toString() -> {
                        logger.log("SignUpViewModel", "password_validation_error_message")
                        _snackBarMessage.send(MessageState(messageId = R.string.password_validation_error_message))
                    }
                    else -> {
                        logger.log("SignUpViewModel", "password_validation_error_message")
                        _snackBarMessage.send(MessageState(messageId = R.string.name_validation_error_message))
                    }
                }
            } else if (result.error.errorCause == ErrorCause.API) {
                if (result.error.errorCode == 500) {
                    logger.log("SignUpViewModel", "error_in_server")
                    _snackBarMessage.send(MessageState(messageId = R.string.error_in_server))
                } else {
                    logger.log(
                        "SignUpViewModel", "server error result: " + result.error.errorResponse
                    )
                    _snackBarMessage.send(MessageState(message = result.error.errorResponse))
                }
            } else {
                _snackBarMessage.send(MessageState(messageId = R.string.error_in_server))
            }
        } else {
            _snackBarMessage.send(MessageState(messageId = R.string.error_in_server))
        }
    }
}