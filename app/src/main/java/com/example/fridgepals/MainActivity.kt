package com.example.fridgepals

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
<<<<<<< HEAD
import androidx.compose.runtime.collectAsState
=======
import androidx.compose.runtime.LaunchedEffect
>>>>>>> firebase_copy
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
<<<<<<< HEAD
import androidx.compose.ui.text.font.FontFamily
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
=======
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
>>>>>>> firebase_copy
import com.example.fridgepals.data.FirebaseManager
import com.example.fridgepals.data.model.Address
import com.example.fridgepals.data.model.FridgeItem
import com.example.fridgepals.data.model.Reservations
import com.example.fridgepals.data.model.User
import com.example.fridgepals.repository.FridgeRepository
import com.example.fridgepals.repository.UserRepository
import com.example.fridgepals.ui.theme.FridgePalsTheme
<<<<<<< HEAD
import com.example.fridgepals.ui.view.MainView
import com.example.fridgepals.ui.view.Login
import com.example.fridgepals.ui.view.Register
import com.example.fridgepals.ui.view.Screen
import com.example.fridgepals.ui.view_model.MainViewModel
import com.example.fridgepals.ui.view_model.MainViewState
=======
import com.example.fridgepals.ui.view_model.MainViewState
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.util.Calendar
>>>>>>> firebase_copy

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private val mainViewState by mutableStateOf(MainViewState())
    private var currentItemToEdit by mutableStateOf<FridgeItem?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {

        val mainViewModel = MainViewModel()

        super.onCreate(savedInstanceState)
        auth = Firebase.auth

        setContent {
            FridgePalsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
<<<<<<< HEAD
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
                }
            }
        }
    }
=======
                    // Check if user is logged in and update state
                    mainViewState.isUserLoggedIn = auth.currentUser != null
                    var userId: String = ""

                    // check if a user is currently signed in
                    if (mainViewState.isUserLoggedIn) {
                        val currentUser =
                            auth.currentUser // store currently signed in user in currentUser
                        userId = currentUser?.uid.toString()
                        var fridgeItems by remember { mutableStateOf<List<FridgeItem>>(listOf()) }
                        var allFridgeItems by remember { mutableStateOf<List<FridgeItem>>(listOf()) }
                        var pickupDay by remember { mutableStateOf("") }
                        var reservedItems by remember { mutableStateOf<List<FridgeItem>>(listOf()) }
                        var reservationsList by remember { mutableStateOf<List<Reservations>>(listOf()) }
                        var userName by remember { mutableStateOf("Loading...") }

                        if (currentUser != null) {

                            UserRepository.getUsername { name ->
                                userName = name
                            }

                            FridgeRepository.getFridgeItemsNotReserved(userId,
                                onSuccess = { items ->
                                    fridgeItems = items
                                },
                                onFailure = { /* Handle failure */ }
                            )

                            FridgeRepository.getFoodCategories(
                                onSuccess = { categories ->
                                    mainViewState.categories = categories
                                },
                                onFailure = { println("failed") }
                            )

                            FridgeRepository.getCommunityFridge(
                                currentUserId = userId,
                                onSuccess = { items ->
                                    allFridgeItems = items
                                },
                                onFailure = { /* Handle failure */ }
                            )


                            // User is logged in, show add item form
                            AddItemToFridgeForm(userName, mainViewState.categories, { fridgeItem ->
                                // Call function in UserRepository to add item to fridge
                                FridgeRepository.addItemToFridge(userId, fridgeItem,
                                    onSuccess = { println("added successfully item") },
                                    onFailure = { println("failed") }
                                )
                            }, {
                                UserRepository.logoutUser()
                                mainViewState.isUserLoggedIn = false
                            }, pickupDay,
                                onPickupDayChange = { newPickupDay -> pickupDay = newPickupDay })
                           /* Box(
                                modifier = Modifier.padding(top = 450.dp)
                            ) {
                                FridgeItemList(fridgeItems, onEditItem = { item ->
                                    currentItemToEdit = item // Set the item to be edited
                                }, onDeleteItem = { item ->
                                    FridgeRepository.deleteFridgeItem(
                                        userId,
                                        item.itemId,
                                        onSuccess = {
                                            FridgeRepository.getFridgeItemsNotReserved(
                                                userId,
                                                onSuccess = { items -> fridgeItems = items },
                                                onFailure = { println("failed") })
                                        },
                                        onFailure = { error ->
                                            // handle error
                                        })
                                })*/
                              /* Box(modifier = Modifier.size(300.dp).padding(top = 475.dp)) {
                                    AllUsersFridgeItemsList(
                                        allFridgeItems, onReserve = { offeringUserId, itemId ->
                                            val reservingUserId = userId
                                            FridgeRepository.reserveItem(
                                                reservingUserId,
                                                offeringUserId,
                                                itemId,
                                                {},
                                                {})
                                        }
                                    )
                                }*/
                          FridgeRepository.getReservedItems(userId,
                                onSuccess = { items ->
                                            reservedItems = items
                                            },
                                onFailure = { /* Handle failure */ }
                            )

                            FridgeRepository.getReservations(userId,
                                onSuccess = {   reservations ->
                                    reservationsList = reservations
                                },
                                onFailure = { /* Handle Failure */})

                          Box(modifier = Modifier
                              .size(300.dp)
                              .padding(top = 475.dp)) { ReservationsList(reservedItems, reservationsList, onCancel = { reservationId ->
                                FridgeRepository.deleteReservation(userId, reservationId, onSuccess = {}, onFailure = {})
                            })}
                            }
                            /*if (currentItemToEdit != null) {
                                EditFridgeItemDialog(
                                    categories = mainViewState.categories,
                                    item = currentItemToEdit!!,
                                    onDismiss = { currentItemToEdit = null },
                                    onConfirm = { updatedItem ->
                                        FridgeRepository.editFridgeItem(userId,
                                            currentItemToEdit!!.itemId,
                                            updatedItem,
                                            onSuccess = {
                                                FridgeRepository.getFridgeItemsNotReserved(
                                                    userId,
                                                    onSuccess = { items ->
                                                        fridgeItems = items
                                                    },
                                                    onFailure = { println("failed") })
                                                currentItemToEdit = null
                                            },
                                            onFailure = { error ->
                                                // Handle error
                                                currentItemToEdit = null
                                            }
                                        )
                                    }
                                )

                            }*/



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
                                        registrationMessage =
                                            "Registration failed: $errorMessage"
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
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") })
        TextField(value = city, onValueChange = { city = it }, label = { Text("City") })
        TextField(value = street, onValueChange = { street = it }, label = { Text("Street") })

        Button(
            onClick = {
                if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && city.isNotEmpty() && street.isNotEmpty()) {
                    val userAddress = Address(city, street)
                    val user = User(name, email, userAddress, fridge = emptyMap())
                    onRegistrationComplete(email, password, user)
                }
            },
            enabled = name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && city.isNotEmpty() && street.isNotEmpty()
        ) {
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
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") })

        Button(onClick = { onLoginComplete(email, password) }) {
            Text("Login")
        }
        if (message.isNotEmpty()) {
            Text(message)
        }
    }
}

@Composable
fun AddItemToFridgeForm(
    userName: String,
    categories: List<String>,
    onItemAdd: (FridgeItem) -> Unit,
    onLogout: () -> Unit,
    pickupDay: String,
    onPickupDayChange: (String) -> Unit,
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    // State variables for form fields
    var itemName by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }
    var newPickupDay by remember { mutableStateOf("") }
    var pickupTime by remember { mutableStateOf("") }

    Column {
        Text(text = "Logged in as: $userName")

        Button(onClick = { onLogout() }) {
            Text("Logout")
        }

        TextField(
            value = itemName,
            onValueChange = { itemName = it },
            label = { Text("Item Name") })
        TextField(
            value = quantity,
            onValueChange = { quantity = it },
            label = { Text("Quantity") })
        CategoryDropdownMenu(categories, selectedCategory, onCategorySelected = { category ->
            selectedCategory = category
        })
        TextField(
            value = pickupDay,
            onValueChange = { newPickupDay = it },
            label = { Text("Pickup Day") })
        Button(onClick = {
            val datePickerDialog = DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    onPickupDayChange("$dayOfMonth.${month + 1}.$year")
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }) {
            Text(if (pickupDay.isNotEmpty()) pickupDay else "Select Pickup Day")
        }
        TextField(
            value = pickupTime,
            onValueChange = { pickupTime = it },
            label = { Text("Pickup Time") })

        Button(
            onClick = {
                val fridgeItem = FridgeItem(
                    name = itemName,
                    quantity = quantity,
                    category = selectedCategory,
                    pickupDay = pickupDay,
                    pickupTime = pickupTime
                )
                if (itemName.isNotEmpty() && quantity.isNotEmpty() && selectedCategory.isNotEmpty() && pickupDay.isNotEmpty() && pickupTime.isNotEmpty()) {
                    onItemAdd(fridgeItem)
                }
            },
            enabled = itemName.isNotEmpty() && quantity.isNotEmpty() && selectedCategory.isNotEmpty() && pickupDay.isNotEmpty() && pickupTime.isNotEmpty()
        ) {
            Text("Add to Fridge")
        }
    }
}

@Composable
fun CategoryDropdownMenu(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Text(
        text = selectedCategory,
        modifier = Modifier.clickable { expanded = true }
    )

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        categories.forEach { name ->
            DropdownMenuItem(text = {
                Text(text = name)
            },
                onClick = {
                    onCategorySelected(name)
                    expanded = false
                })
        }
    }
    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
}

@Composable
fun FridgeItemList(
    fridgeItems: List<FridgeItem>,
    onEditItem: (FridgeItem) -> Unit,
    onDeleteItem: (FridgeItem) -> Unit
) {
    if (fridgeItems.isEmpty()) {
        Text("Your fridge is empty")
    } else {
        LazyColumn {
            items(fridgeItems) { item ->
                FridgeItemRow(item, onEdit = onEditItem, onDelete = onDeleteItem)
            }
        }
    }
}

@Composable
fun FridgeItemRow(item: FridgeItem, onEdit: (FridgeItem) -> Unit, onDelete: (FridgeItem) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Item: ${item.name}")
            Text(text = "Quantity: ${item.quantity}")
            Text(text = "Category: ${item.category}")
            Text(text = "Pickup Day: ${item.pickupDay}")
            Text(text = "Pickup Time: ${item.pickupTime}")
        }
        Button(onClick = { onEdit(item) }) {
            Text("Edit")
        }
        Button(onClick = { onDelete(item) }) {
            Text("Delete")
        }
    }
}

@Composable
fun EditFridgeItemDialog(
    categories: List<String>,
    item: FridgeItem,
    onDismiss: () -> Unit,
    onConfirm: (FridgeItem) -> Unit
) {
    var itemName by remember { mutableStateOf(item.name) }
    var quantity by remember { mutableStateOf(item.quantity) }
    var selectedCategory by remember { mutableStateOf(item.category) }
    var pickupDay by remember { mutableStateOf(item.pickupDay) }
    var pickupTime by remember { mutableStateOf(item.pickupTime) }


    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Fridge Item") },
        text = {
            Column {
                TextField(
                    value = itemName,
                    onValueChange = { itemName = it },
                    label = { Text("Item Name") }
                )
                TextField(value = quantity,
                    onValueChange = { quantity = it },
                    label = { Text("Quantity") }
                )
                CategoryDropdownMenu(
                    categories,
                    selectedCategory,
                    onCategorySelected = { category ->
                        selectedCategory = category
                    })
                TextField(
                    value = pickupDay,
                    onValueChange = { pickupDay = it },
                    label = { Text("Pickup Day") })
                TextField(
                    value = pickupTime,
                    onValueChange = { pickupTime = it },
                    label = { Text("Pickup Time") })
            }
        },
        confirmButton = {
            Button(onClick = {
                val updatedItem = item.copy(
                    name = itemName,
                    quantity = quantity,
                    category = selectedCategory,
                    pickupDay = pickupDay,
                    pickupTime = pickupTime
                )
                onConfirm(updatedItem)
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun AllUsersFridgeItemsList(fridgeItems: List<FridgeItem>, onReserve: (String, String) -> Unit) {
    if (fridgeItems.isEmpty()) {
        Text("No items available in the community fridge")
    } else {
        Text(text = "This is the community fridge")
        LazyColumn {
            items(fridgeItems) { item ->
                AllFridgeItemsRow(item, onReserve)
            }
        }
    }
}

@Composable
fun AllFridgeItemsRow(item: FridgeItem, onReserve: (String, String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Item: ${item.name}")
            Text(text = "Quantity: ${item.quantity}")
            Text(text = "Category: ${item.category}")
            Text(text = "Pickup Day: ${item.pickupDay}")
            Text(text = "Pickup Time: ${item.pickupTime}")
        }
        Button(onClick = { onReserve(item.ownerId, item.itemId) }) {
            Text("Reserve")
        }
    }
}

@Composable
fun ReservationsList(reservedItems: List<FridgeItem>, reservations: List<Reservations>, onCancel: (String) -> Unit) {

    if (reservations.isEmpty()) {
        Text("You have not reserved any items")
    } else {
        LazyColumn {
            items(reservedItems) { item ->
                // Find the corresponding reservation
                val reservation = reservations.find {it.itemId == item.itemId}
                reservation?.let {ReservedItemRow(item, it, onCancel)}
            }
        }
    }
}

@Composable
fun ReservedItemRow(item: FridgeItem,reservations: Reservations, onCancel: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Item: ${item.name}")
            Text(text = "Quantity: ${item.quantity}")
            Text(text = "Category: ${item.category}")
            Text(text = "Pickup Day: ${item.pickupDay}")
            Text(text = "Pickup Time: ${item.pickupTime}")
            Button(onClick = { onCancel(reservations.reservationId) }) {
                Text("Cancel Reservation")
            }
        }
    }
}

>>>>>>> firebase_copy
