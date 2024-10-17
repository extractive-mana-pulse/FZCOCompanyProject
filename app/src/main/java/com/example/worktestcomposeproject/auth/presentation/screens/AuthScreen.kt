package com.example.worktestcomposeproject.auth.presentation.screens

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
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
import com.example.worktestcomposeproject.util.manager.SessionManager
import com.togitech.ccp.component.TogiCountryCodePicker
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {

    val viewModel = hiltViewModel<AuthViewModel>()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    var code: String by rememberSaveable { mutableStateOf("") }
    var isCodeLayoutVisible by remember { mutableStateOf(false) }
    var phoneNumber: String by rememberSaveable { mutableStateOf("") }
    var fullPhoneNumber: String by rememberSaveable { mutableStateOf("") }
    var isNumberValid: Boolean by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
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

        Text(
            text = "Don't have an account? Register",
            fontSize = MaterialTheme.typography.bodySmall.fontSize,
            modifier = modifier
                .clickable { navController.navigate(Screens.Register.route) }
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .align(Alignment.End),
        )

        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            EditNumberField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                value = code,
                onValueChange = {
                    if (it.length <= 6 && it.all { char -> char.isDigit() }) {
                        code = it
                    }
                },
                label = R.string.code,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                isVisible = isCodeLayoutVisible
            )

            Button(
                modifier = modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .height(56.dp)
                    .fillMaxWidth(1f),
                onClick = {
                    coroutineScope.launch {
                        viewModel.auth(phone = phoneNumber)
                        viewModel.authResult.collect {
                            when(it) {
                                is AuthResult.Error -> {
                                    Log.d("Error", it.message)
                                }
                                AuthResult.Loading -> {
                                    // TODO: show loading
                                }
                                is AuthResult.Success -> {
//                                    navController.navigate(Screens.Home.route)
                                    isCodeLayoutVisible = true
                                }
                            }
                        }
                    }
                },
                enabled = fullPhoneNumber.isNotEmpty() && isNumberValid,
            ) {
                Text(text = "Next")
            }
            Button(
                modifier = modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(1f)
                    .height(56.dp),
                onClick = {
                    coroutineScope.launch {
                        viewModel.codeCheck(phone = fullPhoneNumber, code = code)
                    }
                }
            ) {
                Text(text = "Submit code and continue")
            }
        }
        coroutineScope.launch {
            viewModel.codeResult.collectLatest { codeValue ->
                when(codeValue) {
                    is CodeResult.Error -> {
                        Log.d("Error", codeValue.message)
                    }
                    CodeResult.Loading -> {
                        // TODO
                    }
                    is CodeResult.Success -> {
                        if (codeValue.data.isUserExist == true) {
                            val token = viewModel.token.value
                            val userId = viewModel.userId.value

                            SessionManager(context).saveToken(token.toString())
                            SessionManager(context).saveUserID(userId.toString())
                            navController.navigate(Screens.Home.route)
                        } else {
                            navController.navigate(Screens.Register.route)
                            Toast.makeText(context, "User not found", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
        Button(
            modifier = modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(1f)
                .height(56.dp),
            onClick = {
                navController.navigate(Screens.Home.route)
            }
        ) {
            Text(text = "Test Button")
        }
    }
}

@Composable
fun EditNumberField(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    @StringRes label : Int,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions
) {
    if (isVisible) {
        TextField(
            modifier = modifier,
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            label = { Text(stringResource(label)) },
            keyboardOptions = keyboardOptions,

        )
    }
}