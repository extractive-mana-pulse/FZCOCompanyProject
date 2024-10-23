package com.example.worktestcomposeproject.profile.data.remote.api

import retrofit2.http.GET

interface UserApi {

    @GET("api/v1/users/me")
    fun getUserCredentials()
}