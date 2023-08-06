package com.example.authdatasource.repository

import android.content.SharedPreferences
import com.example.authdatasource.MockSharedPreferences
import com.example.authdomain.repository.CredentialRepository
import com.example.core.model.Credential
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CredentialRepositoryImplTest {

    lateinit var credentialRepository: CredentialRepository
    lateinit var sharedPreferences: SharedPreferences

    @Before
    fun setUp() {
        sharedPreferences = MockSharedPreferences()
        credentialRepository = CredentialRepositoryImpl(sharedPreferences = sharedPreferences)
    }

    @After
    fun tearDown() {
        sharedPreferences.edit().clear().apply()
    }

    @Test
    fun `save credential successfully`() {
        val mockCredential = Credential(
            userId = 1,
            token = ('a'..'z').joinToString(""),
            refreshToken = ('a'..'x').joinToString(""),
            password = ('a'..'k').joinToString(""),
            name = ('a'..'f').joinToString("")
        )
        credentialRepository.saveCredential(mockCredential)

        val expectedResult = Credential(
            userId = sharedPreferences.getLong("user_id", 0),
            token = sharedPreferences.getString("token", "")!!,
            refreshToken = sharedPreferences.getString("refresh_token", ""),
            password = sharedPreferences.getString("password", ""),
            name = sharedPreferences.getString("name", "")
        )

        assertEquals(expectedResult, mockCredential)
    }

    @Test
    fun `get credential successfully`() {
        val mockCredential = Credential(
            userId = 1,
            token = ('a'..'z').joinToString(""),
            refreshToken = ('a'..'x').joinToString(""),
            password = ('a'..'k').joinToString(""),
            name = ('a'..'f').joinToString("")
        )
        credentialRepository.saveCredential(mockCredential)

        val actualResult = credentialRepository.getCredential()

        assertEquals(mockCredential, actualResult)
    }
}