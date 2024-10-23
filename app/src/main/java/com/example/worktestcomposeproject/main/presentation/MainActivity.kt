package com.example.worktestcomposeproject.main.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.worktestcomposeproject.ui.theme.WorkTestComposeProjectTheme
import com.example.worktestcomposeproject.util.navigation.NavigationGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WorkTestComposeProjectTheme {
                val navController = rememberNavController()
                NavigationGraph(navController = navController)
            }
        }
    }
}