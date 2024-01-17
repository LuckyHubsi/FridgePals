package com.example.fridgepals

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.fridgepals.data.model.Address
import com.example.fridgepals.data.model.User
import com.example.fridgepals.repository.UserRepository
import com.example.fridgepals.ui.theme.FridgePalsTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // User is signed in
            // Redirect to main screen or perform other appropriate actions
        } else {
            // No user is signed in
            // Stay on the login screen or handle accordingly
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

        setContent {
            FridgePalsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var loginMessage by remember { mutableStateOf("") }
                    var registrationMessage by remember { mutableStateOf("") }

                    Column {
                        RegistrationForm(onRegistrationComplete = { email, password, user ->
                            UserRepository.registerUser(email, password, user,
                                onSuccess = {
                                    registrationMessage = "Registration successful!"
                                },
                                onFailure = { errorMessage ->
                                    registrationMessage = "Registration failed: $errorMessage"
                                }
                            )
                        }, registrationMessage)

                        LoginForm(onLoginComplete = { email, password ->
                            UserRepository.loginUser(email, password,
                                onSuccess = {
                                    loginMessage = "You are logged in!"
                                },
                                onFailure = { errorMessage ->
                                    loginMessage = "Login failed: $errorMessage"
                                }
                            )
                        }, loginMessage)
                    }
                }
            }
        }
    }
}

@Composable
fun RegistrationForm(onRegistrationComplete: (String, String, User) -> Unit, message: String) {
    // State variables for form fields (name, email, password, etc.)
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var street by remember { mutableStateOf("") }

    Column {
        TextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
        TextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        TextField(value = password, onValueChange = { password = it }, label = { Text("Password") })
        TextField(value = city, onValueChange = { city = it }, label = { Text("City") })
        TextField(value = street, onValueChange = { street = it }, label = { Text("Street") })

        Button(onClick = {
            val userAddress = Address(city, street)
            val user = User(name, email, userAddress, fridge = emptyMap())
            onRegistrationComplete(email, password, user)
        }) {
            Text("Register")
        }
        if (message.isNotEmpty()) {
            Text(message)
        }
    }
}

@Composable
fun LoginForm(onLoginComplete: (String, String) -> Unit, message: String) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column {

        TextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        TextField(value = password, onValueChange = { password = it }, label = { Text("Password") })

        Button(onClick = { onLoginComplete(email, password) }) {
            Text("Login")
        }
        if (message.isNotEmpty()) {
            Text(message)
        }
    }
}

