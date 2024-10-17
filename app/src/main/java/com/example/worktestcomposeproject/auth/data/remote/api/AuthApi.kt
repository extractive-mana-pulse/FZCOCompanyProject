package com.example.worktestcomposeproject.auth.data.remote.api

import com.example.worktestcomposeproject.auth.domain.model.AuthUser
import com.example.worktestcomposeproject.auth.domain.model.CheckAuthCodeRequest
import com.example.worktestcomposeproject.auth.domain.model.SendAuthCodeRequest
import com.example.worktestcomposeproject.auth.domain.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("/api/v1/users/send-auth-code/")
    fun sendAuthCode(@Body request: SendAuthCodeRequest): Call<SendAuthCodeRequest>

    @POST("/api/v1/users/check-auth-code/")
    fun checkAuthCode(@Body request: CheckAuthCodeRequest): Call<AuthUser>

    @POST("/api/v1/users/register/")
    fun register(@Body request: User): Call<AuthUser>

}