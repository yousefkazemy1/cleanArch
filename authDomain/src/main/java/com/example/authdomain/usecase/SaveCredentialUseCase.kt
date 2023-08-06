package com.example.authdomain.usecase

import com.example.authdomain.repository.CredentialRepository
import com.example.core.model.Credential

class SaveCredentialUseCase(
    private val repository: CredentialRepository
) {
    operator fun invoke(credential: Credential) {
        repository.saveCredential(credential)
    }
}