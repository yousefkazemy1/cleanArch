package com.example.profile.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.model.Result
import com.example.core.utils.login.LoginStatus
import com.example.profile.model.ProfileUI
import com.example.profile.model.convertToProfileUI
import com.example.profiledomain.usecase.GetProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val useCase: GetProfileUseCase,
    private val loginStatus: LoginStatus,
) : ViewModel() {

    val profileState = MutableStateFlow<ProfileUI?>(null)

    fun getProfileInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            val profile = useCase.invoke(loginStatus.userId)
            if (profile is Result.Success) {
                profileState.value = profile.data.convertToProfileUI()
            }
        }
    }
}