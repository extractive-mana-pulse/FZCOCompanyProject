package com.example.worktestcomposeproject.auth.domain.repositoryImpl

import com.example.worktestcomposeproject.auth.data.remote.api.AuthApi
import com.example.worktestcomposeproject.auth.data.remote.repository.AuthRepository
import com.example.worktestcomposeproject.auth.domain.model.AuthUser
import com.example.worktestcomposeproject.auth.domain.model.CheckAuthCodeRequest
import com.example.worktestcomposeproject.auth.domain.model.SendAuthCodeRequest
import com.example.worktestcomposeproject.auth.domain.model.User
import retrofit2.Call

class AuthRepositoryImpl(
    private val authApi: AuthApi
): AuthRepository {

    override suspend fun sendAuthCode(request: SendAuthCodeRequest): Call<SendAuthCodeRequest> {
        return authApi.sendAuthCode(request)
    }

    override suspend fun checkAuthCode(request: CheckAuthCodeRequest): Call<AuthUser> {
        return authApi.checkAuthCode(request)
    }

    override suspend fun register(request: User): Call<AuthUser> {
        return authApi.register(request)
    }

}