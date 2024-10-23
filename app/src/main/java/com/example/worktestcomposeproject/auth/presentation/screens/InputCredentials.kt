package com.example.worktestcomposeproject.auth.presentation.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.commandiron.wheel_picker_compose.WheelDatePicker
import com.example.worktestcomposeproject.R
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Preview(showBackground = true, showSystemUi = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputCredentialsScreen(
    navController: NavHostController = rememberNavController(),
) {

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Input Credentials")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigateUp()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ){

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                Image(
                    modifier = Modifier
                        .padding(vertical = 24.dp)
                        .width(88.dp)
                        .height(90.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .clickable { /* TODO: show photo picker */ },
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null
                )

                NameColumn(
                    firstName = firstName,
                    onFirstNameChange = { firstName = it },
                    firstNamePlaceholder = R.string.first_name,
                    lastNamePlaceholder = R.string.last_name,
                    lastName = lastName,
                    onLastNameChange = { lastName = it }
                )

                BioColumn(
                    bio = bio,
                    onBioChange = { bio = it },
                    bioPlaceholder = R.string.bio
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(vertical = 8.dp, horizontal = 8.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "You can add a few lines about yourself",
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    )
                }
                BirthDayColumn()
            }
            androidx.compose.material3.Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                onClick = {
                    navController.navigate(Screens.Home.route)
                },
            ) {
                Text(text = "Skip all")
            }
        }
    }
}

@Composable
fun NameColumn(
    firstName : String,
    lastName: String,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    @StringRes firstNamePlaceholder : Int,
    @StringRes lastNamePlaceholder : Int,
) {

    val modifier = Modifier

    Column(
        modifier = modifier
            .fillMaxWidth(1f)
            .background(MaterialTheme.colorScheme.surfaceVariant),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Your name",
            textAlign = TextAlign.Start,
            modifier = modifier
                .align(Alignment.Start)
                .padding(8.dp),
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        TextField(
            modifier = modifier.fillMaxWidth(),
            value = firstName,
            onValueChange = { onFirstNameChange(it) },
            placeholder = { Text(text = stringResource(firstNamePlaceholder)) },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        Divider(
            modifier = Modifier.padding(start = 16.dp),
            color = MaterialTheme.colorScheme.outline,
            )
        TextField(
            modifier = modifier.fillMaxWidth(),
            value = lastName,
            onValueChange = { onLastNameChange(it) },
            placeholder = { Text(text = stringResource(lastNamePlaceholder)) },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}

@Composable
fun BioColumn(
    bio : String,
    onBioChange: (String) -> Unit,
    @StringRes bioPlaceholder : Int,
) {
    val modifier = Modifier
    Column(
        modifier = modifier
            .fillMaxWidth(1f)
            .padding(top = 16.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Your bio",
            textAlign = TextAlign.Start,
            modifier = modifier
                .align(Alignment.Start)
                .padding(8.dp),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        TextField(
            modifier = modifier.fillMaxWidth(),
            value = bio,
            onValueChange = { onBioChange(it) },
            placeholder = { Text(text = stringResource(bioPlaceholder)) },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}

@Composable
fun BirthDayColumn() {
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    val modifier = Modifier

    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            Column(modifier = modifier.fillMaxSize()) {
                WheelPicker(
                    modifier = modifier.weight(1f).align(Alignment.CenterHorizontally), // Fill remaining space
                    onDateSelected = { snappedDate ->
                        selectedDate = snappedDate // Update selected date
                    },
                    onDismiss = {
                        scope.launch {
                            bottomSheetState.hide() // Close the bottom sheet if dismissed
                        }
                    }
                )

                Button(
                    onClick = {
                        scope.launch {
                            bottomSheetState.hide() // Close the bottom sheet
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp) // Add some padding around the button
                ) {
                    Text("Save") // Change button text to "Save"
                }
            }
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth(1f)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Your birthday",
                textAlign = TextAlign.Start,
                modifier = modifier
                    .align(Alignment.Start)
                    .padding(8.dp),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Date of Birth",
                    modifier = modifier
                        .padding(8.dp)
                        .weight(1f), // This will take up the available space
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                )

                Text(
                    text = selectedDate?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) ?: "Add",
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            // Open the bottom sheet
                            scope.launch {
                                bottomSheetState.show()
                            }
                        }
                )
            }
        }
    }
}

@Composable
fun WheelPicker(
    modifier: Modifier,
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit
) {
    WheelDatePicker(
        startDate = LocalDate.now(),
        minDate = LocalDate.of(2020, 1, 1),
        maxDate = LocalDate.of(2030, 12, 31),
        textStyle = MaterialTheme.typography.bodyMedium,
        textColor = Color(0xFF000000),
        size = DpSize(250.dp, 150.dp),
    ) { snappedDate ->
        onDateSelected(snappedDate) // Return selected date
    }
}