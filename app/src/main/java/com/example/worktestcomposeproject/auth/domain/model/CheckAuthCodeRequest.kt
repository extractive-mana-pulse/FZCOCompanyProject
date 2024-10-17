package com.example.worktestcomposeproject.auth.domain.model

data class CheckAuthCodeRequest(
    val phone : String,
    val code : String
)