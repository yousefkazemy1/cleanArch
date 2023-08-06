package com.example.authdomain.repository

import com.example.authdomain.model.Auth
import com.example.core.model.Credential
import com.example.core.model.Result

interface AuthRepository {
    suspend fun login(auth: Auth): Result<Credential>

    suspend fun signUp(auth: Auth): Result<Credential>
}