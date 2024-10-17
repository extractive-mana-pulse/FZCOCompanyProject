package com.example.worktestcomposeproject.auth.domain.model

import com.google.gson.annotations.SerializedName

data class AuthUser(
    @SerializedName("id"        ) var id        : String? = null,
    @SerializedName("access_token"     ) var token     : String? = null,
    @SerializedName("refresh_token"     ) var refreshToken     : String? = null,
    @SerializedName("isUserExist"    ) var isUserExist     : Boolean? = null
)