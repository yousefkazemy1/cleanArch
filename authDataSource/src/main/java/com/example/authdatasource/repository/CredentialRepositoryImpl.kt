package com.example.authdatasource.repository

import android.content.SharedPreferences
import com.example.authdomain.repository.CredentialRepository
import com.example.core.model.Credential

class CredentialRepositoryImpl(
    private val sharedPreferences: SharedPreferences,
) : CredentialRepository {
    override fun saveCredential(credential: Credential) {
        sharedPreferences.edit().putLong("user_id", credential.userId).apply()
        sharedPreferences.edit().putString("token", credential.token).apply()
        sharedPreferences.edit().putString("refresh_token", credential.refreshToken).apply()
        sharedPreferences.edit().putString("password", credential.password).apply()
        sharedPreferences.edit().putString("name", credential.name).apply()
    }

    override fun getCredential(): Credential = Credential(
        userId = sharedPreferences.getLong("user_id", 0),
        token = sharedPreferences.getString("token", null) ?: "",
        refreshToken = sharedPreferences.getString("refresh_token", null),
        password = sharedPreferences.getString("password", null),
        name = sharedPreferences.getString("name", null)
    )
}