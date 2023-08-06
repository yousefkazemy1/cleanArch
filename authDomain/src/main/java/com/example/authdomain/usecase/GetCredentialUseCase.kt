package com.example.authdomain.usecase

import com.example.authdomain.repository.CredentialRepository
import com.example.core.model.Credential

class GetCredentialUseCase(
    private val repository: CredentialRepository
) {
    operator fun invoke(): Credential = repository.getCredential()
}