package com.example.worktestcomposeproject.main.presentation.screens

import android.util.Log
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.worktestcomposeproject.R
import com.example.worktestcomposeproject.auth.presentation.screens.Screens
import com.example.worktestcomposeproject.model.FakeUsers
import com.example.worktestcomposeproject.util.extensions.loadJsonFromAssets
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val jsonString = remember { loadJsonFromAssets(context, "final.json") }
    val fakeUsersList = parseJsonUser(jsonString)


    Scaffold(
        modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                scrollBehavior = scrollBehavior,
                actions = {
                    IconButton(
                        onClick = {
                            navController.navigate(Screens.Profile.route)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null
                        )
                    }
                }
            )
        },

    ) { value ->

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
                        UserItem(
                            user,
                            onItemClick = {
                                navController.navigate(Screens.Detail(
                                    picture = it.picture,
                                    title = it.first_name,
                                    email = it.email
                                ))
                            }
                        )
                    }
                }
            }
        }
    }
}

fun parseJsonUser(jsonString: String): List<FakeUsers> {
    val gson = Gson()
    return gson.fromJson(jsonString, object : TypeToken<List<FakeUsers>>() {}.type)
}

@Composable
fun UserItem(user: FakeUsers, onItemClick: (FakeUsers) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp).clickable { onItemClick(user) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Log.d("TAG", "UserItem: ${user.picture}")
        AsyncImage(
            model = "https://example.com/image.jpg",
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.phone),
            placeholder = painterResource(id = R.drawable.ic_launcher_foreground)
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