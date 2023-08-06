package com.example.auth.ui

import com.example.authdomain.repository.CredentialRepository
import com.example.core.model.Credential

class CredentialRepositoryImplFake: CredentialRepository {
    override fun saveCredential(credential: Credential) {

    }

    override fun getCredential(): Credential {
        return Credential(token = "")
    }
}