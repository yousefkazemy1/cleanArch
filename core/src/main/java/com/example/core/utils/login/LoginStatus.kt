package com.example.core.utils.login

import com.example.core.model.Credential

object LoginStatus {
    var isUserLoggedIn: Boolean = false
        private set
    var userId: Long = 0L
        private set

    fun checkIsUserLoggedIn(credential: Credential) {
        isUserLoggedIn = credential.userId > 0 && credential.token.isNotEmpty()
        userId = credential.userId
    }
}