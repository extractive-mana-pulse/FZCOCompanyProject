package com.example.worktestcomposeproject.profile.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.worktestcomposeproject.R
import com.example.worktestcomposeproject.auth.presentation.screens.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreen(
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Profile")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigateUp()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            navController.navigate(Screens.EditProfile.route)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = R.drawable.ic_launcher_background,
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp)
                    .height(105.dp)
                    .width(105.dp)
                    .clip(shape = RoundedCornerShape(24.dp)),
            )
            TextContainer("Nickname")
            TextContainer("Phone number")
            TextContainer("Town")
            TextContainer("Date of birth")
            TextContainer("Zodiac sign by date of birth")
            TextContainer("Bio")
        }
    }
}

@Composable
fun TextContainer(textContext : String) {
    Box(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .background(MaterialTheme.colorScheme.primaryContainer, shape = RoundedCornerShape(8.dp))
            .border(1.dp, MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = textContext,
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
    }
}