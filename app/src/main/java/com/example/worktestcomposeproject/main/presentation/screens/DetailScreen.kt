package com.example.worktestcomposeproject.main.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage

data class DetailScreenParams(val image: String, val title: String, val email: String)

class DetailScreenPreviewParameterProvider : PreviewParameterProvider<DetailScreenParams> {
    override val values: Sequence<DetailScreenParams> = sequenceOf(
        DetailScreenParams(
            image = "https://example.com/image.png",
            title = "John Doe",
            email = "john.doe@example.com"
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    image: String,
    title: String,
    email: String,
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    val modifier = Modifier
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box{
                        Row{
                            AsyncImage(
                                model = image,
                                contentDescription = "Profile picture",
                                modifier = modifier
                                    .width(48.dp)
                                    .height(48.dp)
                            )
                            Column {
                                Text(
                                    text = title,
                                    fontSize = MaterialTheme.typography.titleMedium.fontSize,

                                    )
                                Text(
                                    text = email,
                                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                                )
                            }
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },

            )
        }
    ) { paddingValues ->
        var text by remember { mutableStateOf(TextFieldValue()) }

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Detail Screen context appear here! ")
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Type a message...") }
                )
                IconButton(
                    onClick = {
                        Toast.makeText(context, "In Progress", Toast.LENGTH_SHORT).show()
                })
                {
                    Icon(Icons.Filled.Send, contentDescription = "Send")
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewDetailScreen(
    @PreviewParameter(DetailScreenPreviewParameterProvider::class) params: DetailScreenParams
) {
    DetailScreen(image = params.image, title = params.title, email = params.email)
}