package com.example.fridgepals

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.fridgepals.repository.UserRepository
import com.example.fridgepals.ui.theme.FridgePalsTheme
import com.example.fridgepals.ui.view.Login
import com.example.fridgepals.ui.view.MainView
import com.example.fridgepals.ui.view.Register
import com.example.fridgepals.ui.view.Screen
import com.example.fridgepals.ui.view_model.MainViewModel

class MainActivity : ComponentActivity() {
    private val mainViewModel = MainViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            val mainViewState by mainViewModel.mainViewState.collectAsState()
            val userId = mainViewState.userId

            FridgePalsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    when (userId) {
                        null -> when (mainViewState.selectedScreen) {
                            Screen.Login -> Login(
                                mainViewModel,
                                onLoginComplete = { email, password ->
                                    UserRepository.loginUser(email, password,
                                        onSuccess = { loggedInUserId ->
                                            mainViewModel.setLoggedInUserId(loggedInUserId)
                                        },
                                        onFailure = {
                                        }
                                    )
                                })

                            Screen.Register -> Register(
                                mainViewModel,
                                onRegistrationComplete = { email, password, user ->
                                    UserRepository.registerUser(email, password, user,
                                        onSuccess = { registeredUserId ->
                                            mainViewModel.setLoggedInUserId(registeredUserId)
                                        },
                                        onFailure = {
                                        })
                                }
                            )

                            else -> {}
                        }

                        else -> MainView(
                            mainViewModel,
                            userId,
                        )

                    }
                }
            }
        }
    }
}
/*


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

 */

