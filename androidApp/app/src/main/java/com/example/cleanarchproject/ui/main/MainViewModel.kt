package com.example.cleanarchproject.ui.main

import androidx.lifecycle.ViewModel
import com.example.authdomain.usecase.GetCredentialUseCase
import com.example.core.utils.login.LoginStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCredentialUseCase: GetCredentialUseCase,
    private val loginStatus: LoginStatus
) : ViewModel() {

    fun checkIsUserLoggedIn() {
        loginStatus.checkIsUserLoggedIn(getCredentialUseCase())
    }

    fun isUserLoggedIn(): Boolean {
        return loginStatus.isUserLoggedIn
    }
}