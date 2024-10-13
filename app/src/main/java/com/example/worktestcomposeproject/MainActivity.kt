package com.example.worktestcomposeproject

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.worktestcomposeproject.ui.theme.WorkTestComposeProjectTheme
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import network.chaintech.cmpcountrycodepicker.model.CountryDetails
import network.chaintech.cmpcountrycodepicker.ui.CountryPickerBasicTextField
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WorkTestComposeProjectTheme {
                MainScreen()
            }
        }
    }
}

fun readJsonFromAssets(context: Context, fileName: String): String {
    return context.assets.open(fileName).bufferedReader().use { it.readText() }
}

//fun parseJsonToModel(jsonString: String): List<Masks> {
//    val gson = Gson()
//    val type = object : TypeToken<Map<String, Masks>>() {}.type
//    val maskMap: Map<String, Masks> = gson.fromJson(jsonString, type)
//    return maskMap.values.toList()  // Convert map values to a list
//}

fun parseJsonToModel(jsonString: String): Map<String, Masks> {
    val gson = Gson()
    val type = object : TypeToken<Map<String, Masks>>() {}.type
    return gson.fromJson(jsonString, type)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CountryPicker(
    modifier: Modifier = Modifier
) {

    val text by remember { mutableStateOf("") }
    var input by remember { mutableStateOf("") }

    val jsonString = readJsonFromAssets(LocalContext.current, "masks.json")
    val phoneMasks = parseJsonToModel(jsonString)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        EditNumberField(
            modifier = modifier,
            label = R.string.phone_number,
            leadingIcon = R.drawable.phone,
            value = text,
            onValueChange = { input = it },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            ),
            masks = phoneMasks
        )
    }
}

@Composable
fun EditNumberField(
    modifier: Modifier = Modifier,
    @StringRes label: Int,
    @DrawableRes leadingIcon: Int,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    masks: Map<String, Masks>
) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue(value)) }
    var currentMask by remember { mutableStateOf("") }

    TextField(
        modifier = modifier.fillMaxWidth(),
        value = textFieldValue,
        onValueChange = { newValue ->
            // Check if the input is empty
            if (newValue.text.isEmpty()) {
                textFieldValue = TextFieldValue("")
                currentMask = ""
                onValueChange("")
                return@TextField
            }

            // Determine the country code based on the first few digits
            val prefix = newValue.text.takeWhile { it.isDigit() }
            val selectedMask = masks.values.find { mask -> prefix.startsWith(mask.cc.replace("+", "")) }

            // If a mask is found, apply it
            if (selectedMask != null) {
                currentMask = selectedMask.mask

                // Apply the mask to the current input
                val formattedValue = applyMask(newValue.text, currentMask)

                // Update the TextFieldValue to reflect the new formatted input
                textFieldValue = TextFieldValue(formattedValue, selection = TextRange(formattedValue.length))
            } else {
                currentMask = ""
                textFieldValue = newValue // No mask found, keep the input as is
            }

            // Update the value
            onValueChange(textFieldValue.text)
        },
        singleLine = true,
        leadingIcon = {
            Icon(
                painter = painterResource(id = leadingIcon),
                contentDescription = null
            )
        },
        label = { Text(stringResource(label)) },
        placeholder = {
            Text(
                text = currentMask.replace("0", " "),
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
            )
        },
        keyboardOptions = keyboardOptions,
    )
}

// Function to apply the mask
fun applyMask(value: String, mask: String): String {
    val digits = value.filter { it.isDigit() }
    var maskedValue = ""
    var digitIndex = 0

    // Replace the placeholders in the mask with actual digits
    for (char in mask) {
        if (char == '0') {
            if (digitIndex < digits.length) {
                maskedValue += digits[digitIndex++]
            } else {
                maskedValue += '0' // Keep placeholders if no more digits
            }
        } else {
            maskedValue += char // Append non-digit characters
        }
    }

    return maskedValue
}


@Composable
fun CountryPickerBasicText() {
    val selectedCountryState: MutableState<CountryDetails?> = remember {
        mutableStateOf(null)
    }
    var mobileNumber by remember {
        mutableStateOf("")
    }

    CountryPickerBasicTextField(
        mobileNumber = mobileNumber,
        defaultCountryCode = "ad",
        onMobileNumberChange = {
            mobileNumber = it
        },
        onCountrySelected = {
            selectedCountryState.value = it
        },
        modifier = Modifier.fillMaxWidth(),
        defaultPaddingValues = PaddingValues(6.dp),
        showCountryFlag = true,
        showCountryPhoneCode = true,
        showCountryName = false,
        showCountryCode = false,
        showArrowDropDown = true,
        spaceAfterCountryFlag = 8.dp,
        spaceAfterCountryPhoneCode = 6.dp,
        spaceAfterCountryName = 6.dp,
        spaceAfterCountryCode = 6.dp,
        label = {
            Text(text = "Mobile Number")
        },
        focusedBorderThickness = 2.dp,
        unfocusedBorderThickness = 1.dp,
        shape = RoundedCornerShape(10.dp),
        verticalDividerColor = Color(0XFFDDDDDD),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0XFFDDDDDD),
            unfocusedBorderColor = Color(0XFFDDDDDD)
        )
    )
}