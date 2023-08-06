package com.example.authdatasource.repository.fake

import com.example.authdomain.model.Auth
import com.example.authdomain.repository.AuthRepository
import com.example.core.model.Credential
import com.example.core.model.ErrorCause
import com.example.core.model.ErrorResult
import com.example.core.model.Result
import kotlin.random.Random

class AuthRepositoryImplFake() : AuthRepository {

    private var userId = 1L

    fun generateCredential(auth: Auth): Credential {
        val username = auth.email ?: auth.username ?: throw Exception("email or username is required to log in")
        return Credential(
            userId = userId++,
            token = "${username}_${auth.password}_${Random.nextInt(0, 100)}",
            refreshToken = "${Random.nextInt(0, 100)}_${username}_${auth.password}",
            password = auth.password,
            name = auth.name
        )
    }

    /**
     * consists of username and password of users
     */
    companion object {
        private val loggedUsers = HashMap<String, Credential>()
    }

    override suspend fun login(auth: Auth): Result<Credential> {
        val username = auth.email ?: auth.username ?: throw Exception("email or username is required to log in")
        val loggedUser = loggedUsers.containsKey(username)
        return if (loggedUser && loggedUsers[username]?.password == auth.password) {
            Result.Success(loggedUsers[username]!!)
        } else {
            Result.Error(
                ErrorResult(
                    errorCause = ErrorCause.API,
                    errorResponse = "username or password is wrong"
                )
            )
        }
    }

    override suspend fun signUp(auth: Auth): Result<Credential> {
        val username = auth.email ?: auth.username ?: throw Exception("email or username is required to log in")
        loggedUsers[username] = generateCredential(auth = auth)
        return Result.Success(loggedUsers[username]!!)
    }
}