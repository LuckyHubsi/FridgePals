package com.example.fridgepals.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fridgepals.ui.view_model.MainViewModel


@Composable
fun Login(mainViewModel: MainViewModel) {
    var email by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }
    var password by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }

    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .clickable {
                // Clear focus when clicking outside text fields
                focusManager.clearFocus()
            },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .width(350.dp)
                .height(250.dp)
                .shadow(10.dp, shape = RoundedCornerShape(16.dp), ambientColor = MaterialTheme.colorScheme.onSecondary)
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Email TextField
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                            newText -> email = newText
                    },
                    label = { Text(text = "Email") },
                    leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                    ,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                    colors = getOutlinedTextFieldColors()

                )

                // Password TextField
                OutlinedTextField(
                    value = password,
                    onValueChange = {
                            newText -> password = newText
                    },
                    label = { Text("Password") },
                    leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    colors = getOutlinedTextFieldColors()
                )

                Button(
                    onClick = { mainViewModel.updateAuth() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(5.dp, shape = CircleShape, ambientColor = MaterialTheme.colorScheme.onSecondary)
                        .height(58.dp)
                    ,
                ) {
                    Text("Login", color = MaterialTheme.colorScheme.onPrimary, fontSize = 22.sp)
                }
            }
        }
        Text(
            text = "New here? Register",
            color = MaterialTheme.colorScheme.onSecondary,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(top = 300.dp)
                .clickable {
                    mainViewModel.selectScreen(Screen.Register)
                }
        )
    }
}


@Composable
fun Register(mainViewModel: MainViewModel) {
    var name by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }
    var city by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }
    var street by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }
    var email by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }
    var password by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }

    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .clickable {
                // Clear focus when clicking outside text fields
                focusManager.clearFocus()
            },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .width(350.dp)
                .height(500.dp) // Adjusted height for additional fields
                .shadow(10.dp, shape = RoundedCornerShape(16.dp), ambientColor = MaterialTheme.colorScheme.onSecondary)
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Name TextField
                OutlinedTextField(
                    value = name,
                    onValueChange = { newText -> name = newText },
                    label = { Text("Name") },
                    leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                    ,
                    colors = getOutlinedTextFieldColors()
                )

                // City TextField
                OutlinedTextField(
                    value = city,
                    onValueChange = { newText -> city = newText },
                    label = { Text("City") },
                    leadingIcon = { Icon(imageVector = Icons.Default.Place, contentDescription = null) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                    ,
                    colors = getOutlinedTextFieldColors()
                )

                // Street TextField
                OutlinedTextField(
                    value = street,
                    onValueChange = { newText -> street = newText },
                    label = { Text("Street") },
                    leadingIcon = { Icon(imageVector = Icons.Default.Home, contentDescription = null) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                    ,
                    colors = getOutlinedTextFieldColors()
                )

                // Email TextField
                OutlinedTextField(
                    value = email,
                    onValueChange = { newText -> email = newText },
                    label = { Text("Email") },
                    leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                    ,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                    colors = getOutlinedTextFieldColors()
                )

                // Password TextField
                OutlinedTextField(
                    value = password,
                    onValueChange = { newText -> password = newText },
                    label = { Text("Password") },
                    leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = null) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    colors = getOutlinedTextFieldColors()
                )

                // Register Button
                Button(
                    onClick = { mainViewModel.updateAuth() },
                    modifier = Modifier.fillMaxWidth()
                        .shadow(5.dp, shape = CircleShape, ambientColor = MaterialTheme.colorScheme.onSecondary)
                        .height(58.dp)
                    ,
                ) {
                    Text("Register", color = MaterialTheme.colorScheme.onPrimary, fontSize = 22.sp)
                }
            }
        }
        Text(
            text = "Already have an account? Login",
            color = MaterialTheme.colorScheme.onSecondary,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(top = 550.dp)
                .clickable {
                    mainViewModel.selectScreen(Screen.Login)
                }
        )
    }
}

@Composable
fun getOutlinedTextFieldColors(): TextFieldColors {
    return TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = MaterialTheme.colorScheme.tertiary,
        unfocusedBorderColor = MaterialTheme.colorScheme.primary,
        focusedLabelColor = MaterialTheme.colorScheme.tertiary,
        unfocusedLabelColor = MaterialTheme.colorScheme.onTertiary,
        cursorColor = MaterialTheme.colorScheme.onSecondary,
        backgroundColor = MaterialTheme.colorScheme.onPrimary
    )
}

