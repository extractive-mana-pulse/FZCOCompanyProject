package com.example.worktestcomposeproject.auth.presentation.screens

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
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


    Scaffold { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.sign_in).uppercase(),
                    textAlign = TextAlign.Justify,
                    fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                    fontWeight = FontWeight.Normal,
                )

                Text(
                    text = stringResource(R.string.phone_info),
                    textAlign = TextAlign.Center,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(horizontal = 64.dp, vertical = 8.dp)
                )

                Spacer(modifier = Modifier.height(48.dp))

                TogiCountryCodePicker(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    onValueChange = { (code, phone), isValid ->
                        Log.d("CCP", "onValueChange: $code $phone -> $isValid")
                        phoneNumber = phone
                        fullPhoneNumber = code + phone
                        isNumberValid = isValid
                    },
                    label = { Text("Phone Number") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                        placeholderColor = MaterialTheme.colorScheme.outline,
                        textColor = MaterialTheme.colorScheme.onSurface,
                    ),
                    showPlaceholder = true,
                    shape = RoundedCornerShape(8.dp)

                )
            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                FloatingActionButton(
                    modifier = Modifier.size(64.dp),
                    contentColor = MaterialTheme.colorScheme.primaryContainer,
                    backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                    onClick = {
                        navController.navigate(Screens.Register.route)
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Forward"
                    )
                }
            }
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