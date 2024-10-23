package com.example.worktestcomposeproject.profile.presentation.screens

import android.widget.Toast
import androidx.annotation.StringRes
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
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Done
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.worktestcomposeproject.R
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfile(
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
//    val viewModel = hiltViewModel<UserViewModel>()

    var bio by remember { mutableStateOf("") }
    var town by remember { mutableStateOf("") }
    var nickname by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }

    var pickedDate by remember { mutableStateOf(LocalDate.now()) }
    val dateDialogState = rememberMaterialDialogState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Edit profile")
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
                            // TODO: implement action
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
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

            TextFieldContainers(
                modifier = Modifier.fillMaxSize(),
                textContext = nickname,
                onValueChange = {
                    nickname = it
                },
                label = R.string.nickname
            )

            TextContainer("Phone number")

            TextContainer("Zodiac sign by date of birth")
            TextFieldContainers(
                textContext = bio,
                onValueChange = {
                    bio = it
                },
                label = R.string.bio
            )

            TextFieldContainers(
                modifier = Modifier.fillMaxSize(),
                textContext = town,
                onValueChange = {
                    town = it
                },
                label = R.string.town
            )

            TextFieldContainers(
                modifier = Modifier.fillMaxSize(),
                textContext = dateOfBirth,
                onValueChange = {
                    dateOfBirth = it
                },
                leadingIcon = {
                    IconButton(
                        onClick = {
                            dateDialogState.show()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = null
                        )
                    }
                },
                label = R.string.date_of_birth
            )

            MaterialDialog(
                dialogState = dateDialogState,
                buttons = {
                    positiveButton(text = "Ok") {
                        // Format the chosen date and update dateOfBirth
                        val formattedDate = pickedDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy"))
                        dateOfBirth = formattedDate
                        Toast.makeText(
                            context,
                            "Selected date: $formattedDate",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    negativeButton(text = "Cancel")
                }
            ) {
                datepicker(
                    initialDate = LocalDate.now(),
                    title = "Pick a date",
                    allowedDateValidator = {
                        it.dayOfMonth % 2 == 1
                    }
                ) {
                    pickedDate = it // Update pickedDate when a date is selected
                }
            }
        }
    }
}

@Composable
fun TextFieldContainers(
    modifier: Modifier = Modifier,
    textContext: String,
    @StringRes label : Int,
    onValueChange: (String) -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
) {

    Box(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .background(MaterialTheme.colorScheme.primaryContainer, shape = RoundedCornerShape(8.dp))
            .border(1.dp, MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(8.dp))
            .fillMaxWidth()
    ) {
        TextField(
            value = textContext,
            onValueChange = onValueChange,
            leadingIcon = leadingIcon,
            label = { Text(text = stringResource(label)) },
        )
    }
}