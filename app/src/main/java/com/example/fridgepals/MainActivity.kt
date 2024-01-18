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
import com.example.fridgepals.data.FirebaseManager
import com.example.fridgepals.data.model.Address
import com.example.fridgepals.data.model.FridgeItem
import com.example.fridgepals.data.model.User
import com.example.fridgepals.repository.FridgeRepository
import com.example.fridgepals.repository.UserRepository
import com.example.fridgepals.ui.theme.FridgePalsTheme
import com.example.fridgepals.ui.view_model.MainViewState
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private val mainViewState by mutableStateOf(MainViewState())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

        setContent {
            FridgePalsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Check if user is logged in and update state
                    mainViewState.isUserLoggedIn = auth.currentUser != null

                    // check if a user is currently signed in
                    if (mainViewState.isUserLoggedIn) {
                        val currentUser = auth.currentUser // store currently signed in user in currentUser
                        var userName by remember { mutableStateOf("Loading...") }

                        if (currentUser != null) {
                            val userId = currentUser.uid // store firebase user uid in userId
                            val userRef = FirebaseManager.database.reference.child("users")
                                .child(userId) // store firebase uid in userRef

                            // get user's name from the realtime database and store it in userName
                            userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    val user = snapshot.getValue(User::class.java)
                                    userName = user?.name ?: "Unknown"
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    userName = "Error fetching name"
                                }
                            })
                        }

                        // User is logged in, show add item form
                        AddItemToFridgeForm(userName, { fridgeItem ->
                            // Call function in UserRepository to add item to fridge
                            FridgeRepository.addItemToFridge(fridgeItem,
                                onSuccess = { println("added successfully item") },
                                onFailure = { println("failed") }
                            )
                        }, {UserRepository.logoutUser()} )
                    } else {

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

@Composable
fun AddItemToFridgeForm(userName: String, onItemAdd: (FridgeItem) -> Unit, onLogout: () -> Unit) {
    // State variables for form fields
    var itemName by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var categoryId by remember { mutableStateOf("") }
    var pickupDay by remember { mutableStateOf("") }
    var pickupTime by remember { mutableStateOf("") }

    Column {
        Text(text = "Logged in as: $userName")

        Button(onClick = { onLogout() }) {
            Text("Logout")
        }

        TextField(value = itemName, onValueChange = { itemName = it }, label = { Text("Item Name") })
        TextField(value = quantity, onValueChange = { quantity = it }, label = { Text("Quantity") })
        TextField(value = categoryId, onValueChange = { categoryId = it }, label = { Text("Category ID") })
        TextField(value = pickupDay, onValueChange = { pickupDay = it }, label = { Text("Pickup Day") })
        TextField(value = pickupTime, onValueChange = { pickupTime = it }, label = { Text("Pickup Time") })

        Button(onClick = {
            val fridgeItem = FridgeItem(
                name = itemName,
                quantity = quantity,
                categoryId = categoryId,
                pickupDay = pickupDay,
                pickupTime = pickupTime
            )
            onItemAdd(fridgeItem)
        }) {
            Text("Add to Fridge")
        }
    }
}


