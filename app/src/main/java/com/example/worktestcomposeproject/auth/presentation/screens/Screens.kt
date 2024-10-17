package com.example.worktestcomposeproject.auth.presentation.screens

import kotlinx.serialization.Serializable

// TODO NOTE: I've no clue but do not make object data object. they will not work! KEEP IT AS IT IS!

@Serializable
sealed class Screens(val route: String) {
    @Serializable
    object Home : Screens("home")
    @Serializable
    object EditProfile : Screens("edit_profile")
    @Serializable
    object Profile : Screens("profile")
    @Serializable
    object Auth : Screens("auth")
    @Serializable
    object Register : Screens("register")
    @Serializable
    data class Detail(
        val picture: String,
        val title: String,
        val email: String
    )
}
