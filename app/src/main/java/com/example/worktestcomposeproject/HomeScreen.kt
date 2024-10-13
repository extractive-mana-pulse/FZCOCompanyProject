package com.example.worktestcomposeproject

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Preview(showBackground = true, showSystemUi = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    val jsonString = remember { loadJsonFromAssets(context, "final.json") }
    val fakeUsersList = parseJsonUser(jsonString)
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }

    ) { value ->

        NavHost(
            navController = navController,
            startDestination = Home
        ) {
            composable<Home> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(value)
                ) {
                    LazyColumn {
                        item {
                            Spacer(modifier = modifier.height(8.dp))
                        }
                        if (fakeUsersList.isNotEmpty()) {
                            items(fakeUsersList.take(15)) { user ->
                                UserItem(user)
                            }
                        } else {
                            item {
                                Text(text = "No users found")
                            }
                        }
                    }
                }
            }
            composable<Chats> {
                ChatsScreen(navController = navController)
            }
            composable<Settings> {
                SettingsScreen(navController = navController)
            }
        }
    }
}

@Composable
fun SettingsScreen(
    modifier: Modifier= Modifier,
    navController: NavHostController
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) { Text(text = "Settings Screen") }
}


@Composable
fun ChatsScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) { Text(text = "Chats Screen") }
}

fun loadJsonFromAssets(context: Context, fileName: String): String {
    return context.assets.open(fileName).bufferedReader().use { it.readText() }
}

fun parseJsonUser(jsonString: String): List<FakeUsers> {
    val gson = Gson()
    return gson.fromJson(jsonString, object : TypeToken<List<FakeUsers>>() {}.type)
}

@Composable
fun UserItem(user: FakeUsers) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = user.picture,
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = user.first_name, style = MaterialTheme.typography.bodyLarge)
            Text(text = user.email, style = MaterialTheme.typography.bodyMedium)
            HorizontalDivider(
                thickness = 1.dp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}