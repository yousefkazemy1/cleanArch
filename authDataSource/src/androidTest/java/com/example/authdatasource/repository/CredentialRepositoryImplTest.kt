package com.example.authdatasource.repository

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.authdomain.repository.CredentialRepository
import com.example.core.model.Credential
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CredentialRepositoryImplTest {

    lateinit var repository: CredentialRepository

    @Before
    fun setUp() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val sharedPref = appContext.getSharedPreferences("shared.credential", Context.MODE_PRIVATE)
        repository = CredentialRepositoryImpl(
            sharedPref
        )
    }

    @Test
    fun `saveAndGetCredentialSuccessfully`() {
        val mockCredential = Credential(
            userId = 1,
            token = ('a'..'z').joinToString(""),
            refreshToken = ('a'..'x').joinToString(""),
            password = ('a'..'k').joinToString(""),
            name = ('a'..'f').joinToString("")
        )
        repository.saveCredential(mockCredential)

        val expectedResult = repository.getCredential()

        Assert.assertEquals(expectedResult, mockCredential)
    }
}