package com.example.core.utils.login

import com.example.core.model.Credential
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


class LoginStatusTest {

    @Test
    fun userLoggedIn() {
        // abcdefghijklmnopqrstuvwxyz
        val mockToken = ('a'..'z').joinToString("")
        val mockCredential = Credential(
            userId = 1,
            token = mockToken
        )
        LoginStatus.checkIsUserLoggedIn(credential = mockCredential)
        Assertions.assertEquals(true, LoginStatus.isUserLoggedIn)
    }

    @Test
    fun userNotLoggedIn() {
        val mockCredential = Credential(
            userId = 0,
            token = ""
        )
        LoginStatus.checkIsUserLoggedIn(credential = mockCredential)
        Assertions.assertEquals(false, LoginStatus.isUserLoggedIn)
    }
}