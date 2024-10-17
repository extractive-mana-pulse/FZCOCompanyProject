package com.example.worktestcomposeproject.util.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.worktestcomposeproject.auth.presentation.screens.AuthScreen
import com.example.worktestcomposeproject.auth.presentation.screens.RegisterScreen
import com.example.worktestcomposeproject.auth.presentation.screens.Screens
import com.example.worktestcomposeproject.main.presentation.screens.HomeScreen
import com.example.worktestcomposeproject.profile.presentation.screens.EditProfile
import com.example.worktestcomposeproject.profile.presentation.screens.ProfileScreen

@Composable
fun NavigationGraph(navController: NavHostController) {

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
        composable<Screens.Detail> {
            DetailScreen(navController = navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavHostController = rememberNavController()
) {
    val modifier = Modifier
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Detail") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigateUp()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    )
    { paddingValues ->
        Column(
            modifier = modifier.fillMaxSize().padding(paddingValues)
        ) {

        }
    }
}
