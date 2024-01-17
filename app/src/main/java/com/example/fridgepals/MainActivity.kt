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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fridgepals.data.FirebaseManager
import com.example.fridgepals.data.model.Address
import com.example.fridgepals.data.model.User
import com.example.fridgepals.ui.theme.FridgePalsTheme
import com.example.fridgepals.ui.view.MainView
import com.example.fridgepals.ui.view.Login
import com.example.fridgepals.ui.view.Register
import com.example.fridgepals.ui.view.Screen
import com.example.fridgepals.ui.view_model.MainViewModel
import com.example.fridgepals.ui.view_model.MainViewState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val mainViewModel = MainViewModel()

        super.onCreate(savedInstanceState)
        setContent {
            FridgePalsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val state = mainViewModel.mainViewState.collectAsState()

                    if (state.value.authenticator) {
                        MainView(mainViewModel)
                    } else
                // Display the appropriate screen based on selectedScreen
                when (state.value.selectedScreen) {
                    Screen.Login -> Login(mainViewModel)
                    Screen.Register -> Register(mainViewModel)
                    else -> {}
                }
            }

                    /*
                    RegistrationForm(onRegistrationComplete = { user ->
                        FirebaseManager.registerUser(user)
                    })
                     */
                }
            }
        }
    }



@Composable
fun RegistrationForm(onRegistrationComplete: (User) -> Unit) {
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
            val hashedPassword = FirebaseManager.hashPassword(password)
            val userAddress = Address(city, street)
            val user = User(name, email, hashedPassword, userAddress, fridge = emptyMap())
            onRegistrationComplete(user)
        }) {
            Text("Register")
        }
    }
}