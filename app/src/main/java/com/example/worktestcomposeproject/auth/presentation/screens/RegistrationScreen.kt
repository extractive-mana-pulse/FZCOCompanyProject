package com.example.worktestcomposeproject.auth.presentation.screens

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.worktestcomposeproject.R
import com.example.worktestcomposeproject.auth.presentation.vm.AuthResult
import com.example.worktestcomposeproject.auth.presentation.vm.AuthViewModel
import com.example.worktestcomposeproject.auth.presentation.vm.CodeResult
import com.togitech.ccp.component.TogiCountryCodePicker
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val viewModel = hiltViewModel<AuthViewModel>()
    var name: String by rememberSaveable { mutableStateOf("") }
    var username: String by rememberSaveable { mutableStateOf("") }
    var phoneNumber: String by rememberSaveable { mutableStateOf("") }
    var fullPhoneNumber: String by rememberSaveable { mutableStateOf("") }
    var isNumberValid: Boolean by rememberSaveable { mutableStateOf(false) }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Register") },
                navigationIcon = {

                    IconButton(
                        onClick = { navController.navigateUp() },
                        content = {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    )
                },
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            TogiCountryCodePicker(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                onValueChange = { (code, phone), isValid ->
                    Log.d("CCP", "onValueChange: $code $phone -> $isValid")

                    phoneNumber = phone
                    fullPhoneNumber = code + phone
                    isNumberValid = isValid
                },
                label = { Text("Phone Number") },
            )

            EditNumberField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                value = name,
                onValueChange = { name = it },
                label = R.string.name,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                isVisible = true
            )

            EditNumberField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                value = username,
                onValueChange = { username = it },
                label = R.string.username,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                isVisible = true
            )
            Button(
                modifier = modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .fillMaxWidth(1f)
                    .height(56.dp),
                onClick = {
                    coroutineScope.launch { viewModel.registration(fullPhoneNumber, name, username) }
                },
                enabled = fullPhoneNumber.isNotEmpty() && isNumberValid && name.isNotEmpty() && username.isNotEmpty(),
            ) {
                Text(text = "Next")
            }
            coroutineScope.launch {
                viewModel.regResult.collect {
                    when(it) {

                        is CodeResult.Error -> {
                            Log.d("Error", it.message)
                        }
                        CodeResult.Loading -> {
                            // TODO
                        }
                        is CodeResult.Success -> {
                            Log.d("token",it.data.token.toString())
                            Log.d("token: Id",it.data.id.toString())
                            Log.d("token: Exist",it.data.isUserExist.toString())
                            Log.d("token: refresh",it.data.refreshToken.toString())
                            Toast.makeText(context, "You are successfully pass registration your account", Toast.LENGTH_SHORT).show()
                            navController.navigate(Screens.Home.route)
                        }
                    }
                }
            }
        }
    }
}