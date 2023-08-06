package com.example.authdomain.usecase

import com.example.authdomain.repository.CredentialRepository
import com.example.core.model.Credential
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito


class GetCredentialUseCaseTest {

    @Test
    fun `get credential successfully`() {
        val mockCredential = Credential(token = "")
        val repository = Mockito.mock(CredentialRepository::class.java)
        Mockito.`when`(repository.getCredential())
            .thenReturn(mockCredential)

        val usecase = GetCredentialUseCase(repository = repository)
        Assertions.assertEquals(mockCredential, usecase.invoke())
    }
}