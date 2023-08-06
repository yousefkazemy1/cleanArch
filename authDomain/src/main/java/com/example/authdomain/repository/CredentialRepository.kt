package com.example.authdomain.repository

import com.example.core.model.Credential

interface CredentialRepository {
    fun saveCredential(credential: Credential)

    fun getCredential(): Credential
}