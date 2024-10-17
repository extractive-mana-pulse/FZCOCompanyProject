package com.example.worktestcomposeproject.auth.data.remote.repository

import com.example.worktestcomposeproject.auth.domain.model.AuthUser
import com.example.worktestcomposeproject.auth.domain.model.CheckAuthCodeRequest
import com.example.worktestcomposeproject.auth.domain.model.SendAuthCodeRequest
import com.example.worktestcomposeproject.auth.domain.model.User
import retrofit2.Call

interface AuthRepository {

    suspend fun sendAuthCode(request: SendAuthCodeRequest): Call<SendAuthCodeRequest>

    suspend fun checkAuthCode(request: CheckAuthCodeRequest): Call<AuthUser>

    suspend fun register(request: User): Call<AuthUser>

}