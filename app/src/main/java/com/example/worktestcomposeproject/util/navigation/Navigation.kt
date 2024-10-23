package com.example.worktestcomposeproject.util.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.worktestcomposeproject.auth.presentation.screens.AuthScreen
import com.example.worktestcomposeproject.auth.presentation.screens.InputCredentialsScreen
import com.example.worktestcomposeproject.auth.presentation.screens.RegisterScreen
import com.example.worktestcomposeproject.auth.presentation.screens.Screens
import com.example.worktestcomposeproject.main.presentation.screens.DetailScreen
import com.example.worktestcomposeproject.main.presentation.screens.HomeScreen
import com.example.worktestcomposeproject.profile.presentation.screens.EditProfile
import com.example.worktestcomposeproject.profile.presentation.screens.ProfileScreen

@Composable
fun NavigationGraph(
    navController: NavHostController = rememberNavController()
) {

    NavHost(
        navController = navController,
        startDestination = Screens.Auth.route
    ) {
        composable(Screens.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(Screens.EditProfile.route) {
            EditProfile(navController = navController)
        }
        composable(Screens.Profile.route) {
            ProfileScreen(navController = navController)
        }
        composable(Screens.Auth.route) {
            AuthScreen(navController = navController)
        }
        composable(Screens.Register.route) {
            RegisterScreen(navController = navController)
        }
        composable(Screens.InputCredentials.route) {
            InputCredentialsScreen(navController = navController)
        }
        composable<Screens.Detail> {
            val args = it.toRoute<Screens.Detail>()
            DetailScreen(
                image = args.picture,
                title = args.title,
                email = args.email,
                navController = navController
            )
        }
    }
}