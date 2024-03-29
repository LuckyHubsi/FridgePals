package com.example.fridgepals.ui.view


import android.annotation.SuppressLint
import android.widget.Toast
import android.app.DatePickerDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fridgepals.R
import com.example.fridgepals.data.model.FridgeItem
import com.example.fridgepals.data.model.Reservations
import com.example.fridgepals.ui.view_model.MainViewModel
import java.util.Calendar

@Composable
fun getCategoryIcon(category: String): Int {
    return when (category) {
        "Vegetables" -> R.drawable.category_vegetables
        "Fruits" -> R.drawable.category_fruits
        "Meat" -> R.drawable.category_meat
        "Dairy" -> R.drawable.category_dairy
        "Grains" -> R.drawable.category_grains
        "Leftovers" -> R.drawable.category_leftovers
        "Seafood" -> R.drawable.category_seafood
        "Sweets" -> R.drawable.category_sweets
        "Beverages" -> R.drawable.category_beverages
        else -> R.drawable.ic_launcher // Provide a default icon or handle unknown categories
    }
}

@Composable
fun RoundedCard(
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    onEditClick: () -> Unit = {},
    onRemoveClick: () -> Unit = {},
    buttonContent: @Composable () -> Unit,
    category: String,
    item: FridgeItem
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(185.dp)
            .padding(10.dp)
            .shadow(
                5.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = MaterialTheme.colorScheme.onSecondary
            )
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.secondary)
    ) {
        // First row: Image and TextBlock
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            // Image
            Icon(
                painter = painterResource(id = getCategoryIcon(category)),
                contentDescription = null,
                modifier = imageModifier
                    .size(85.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .padding(8.dp)
                ,
                tint = MaterialTheme.colorScheme.primary
            )

            // TextBlock
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 8.dp)
            ) {
                androidx.compose.material3.Text(
                    "${item.quantity} ${item.name}",
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                androidx.compose.material3.Text(
                    "${item.category}",
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                androidx.compose.material3.Text(
                    "${item.pickupDay}, ${item.pickupTime}",
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        // Second row: Custom buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .align(Alignment.BottomEnd),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            // Use the provided @Composable function for custom buttons
            buttonContent()
        }
    }
}

@Composable
fun ButtonContentOwnFridge(
    mainViewModel: MainViewModel,
    onDelete: (FridgeItem) -> Unit,
    item: FridgeItem,
    onEdit: (FridgeItem) -> Unit
) {
    // Second row: Edit and Remove buttons
    Button(
        onClick = {
            onDelete(item)
        },
        modifier = Modifier
            .width(135.dp)
            .height(60.dp)
            .padding(top = 5.dp)
            .shadow(
                5.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = MaterialTheme.colorScheme.onSecondary
            )
            .clip(RoundedCornerShape(20.dp)),
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
    ) {
        androidx.compose.material.Text(
            "Remove",
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 20.sp
        )
    }

    Button(
        onClick = {
            onEdit(item)
        },
        modifier = Modifier
            .width(135.dp)
            .height(60.dp)
            .padding(top = 5.dp)
            .shadow(
                5.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = MaterialTheme.colorScheme.onSecondary
            )
            .clip(RoundedCornerShape(20.dp)),
        shape = RectangleShape
    ) {
        androidx.compose.material.Text(
            "Edit",
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 20.sp
        )
    }
}

@Composable
fun ButtonContentCommunityFridge(item: FridgeItem, onReserve: (String, String) -> Unit) {
    Button(
        onClick = { onReserve(item.ownerId, item.itemId) },
        modifier = Modifier
            .width(250.dp)
            .height(60.dp)
            .padding(top = 5.dp)
            .shadow(
                5.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = MaterialTheme.colorScheme.onSecondary
            )
            .clip(RoundedCornerShape(20.dp)),
        shape = RectangleShape
    ) {
        androidx.compose.material.Text(
            "Reserve",
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 20.sp
        )
    }
}

@Composable
fun ButtonContentReservedItems(
    item: FridgeItem,
    reservations: Reservations,
    onCancel: (String) -> Unit
) {
    Button(
        onClick = { onCancel(reservations.reservationId) },
        modifier = Modifier
            .width(265.dp)
            .height(60.dp)
            .padding(top = 5.dp)
            .shadow(
                5.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = MaterialTheme.colorScheme.onSecondary
            )
            .clip(RoundedCornerShape(20.dp)),
        shape = RectangleShape
    ) {
        androidx.compose.material.Text(
            "Cancel Reservation",
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 20.sp
        )
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopUp_Edit(
    mainViewModel: MainViewModel,
    userId: String,
    item: FridgeItem,
) {
    val state = mainViewModel.mainViewState.collectAsState()
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    var name by remember {
        mutableStateOf((item.name))
    }
    var quantity by remember {
        mutableStateOf((item.quantity))
    }

    var selectedCategory by remember { mutableStateOf(item.category) }

    var pickup_time by remember {
        mutableStateOf((item.pickupTime))
    }


    if (state.value.currentItemToEdit != null)
        AlertDialog(
            containerColor = MaterialTheme.colorScheme.secondary,
            onDismissRequest = {
                mainViewModel.stopEditingFridgeItem()
            },
            confirmButton = {},
            text = {
                Column(
                ) {
                    // Name
                    OutlinedTextField(
                        value = name,
                        onValueChange = { newText -> name = newText },
                        label = { androidx.compose.material.Text("Name") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        colors = getOutlinedTextFieldColors()
                    )

                    // Quantity
                    OutlinedTextField(
                        value = quantity,
                        onValueChange = { newText -> quantity = newText },
                        label = { androidx.compose.material.Text("Quantity") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        colors = getOutlinedTextFieldColors()
                    )

                    CategoryDropdownMenu(
                        mainViewModel.mainViewState.value.listOfCategories,
                        selectedCategory,
                        onCategorySelected = { category ->
                            selectedCategory = category
                        })

                    // Pickup Date
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                            .background(MaterialTheme.colorScheme.primary),
                        onClick = {
                            val datePickerDialog = DatePickerDialog(
                                context,
                                { _, year, month, dayOfMonth ->
                                    mainViewModel.setPickupDay("$dayOfMonth.${month + 1}.$year")
                                },
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)
                            )
                            datePickerDialog.show()
                        }) {
                        Text(
                            text = if (state.value.pickupDay.isNotEmpty()) state.value.pickupDay else "Select Pickup Day",
                            color = Color.White // Set text color to match the outlinedTextField
                        )
                    }

                    // Pickup Time
                    OutlinedTextField(
                        value = pickup_time,
                        onValueChange = { newText -> pickup_time = newText },
                        label = { androidx.compose.material.Text("Pick-up time") },
/*                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.Face,
                                contentDescription = null
                            )
                        },*/
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        colors = getOutlinedTextFieldColors()
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 25.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = {
                                mainViewModel.stopEditingFridgeItem()
                            },
                            modifier = Modifier
                                .width(125.dp)
                                .height(60.dp)
                                .padding(top = 5.dp)
                                .shadow(
                                    5.dp,
                                    shape = RoundedCornerShape(20.dp),
                                    ambientColor = MaterialTheme.colorScheme.onSecondary
                                )
                                .clip(RoundedCornerShape(20.dp)),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                            shape = RectangleShape,
                        ) {
                            androidx.compose.material.Text(
                                "Cancel",
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 16.sp
                            )
                        }

                        Button(
                            modifier = Modifier
                                .width(125.dp)
                                .height(60.dp)
                                .padding(top = 5.dp)
                                .shadow(
                                    5.dp,
                                    shape = RoundedCornerShape(28.dp),
                                    ambientColor = MaterialTheme.colorScheme.onSecondary
                                )
                                .clip(RoundedCornerShape(20.dp)),
                            shape = RectangleShape,
                            onClick = {
                                mainViewModel.mainViewState.value.currentItemToEdit = null
                                mainViewModel.refreshOwnFridgeItemsNotReserved(userId)
                                mainViewModel.refreshOwnFridgeItemsReserved(userId)
                                val updatedItem = item.copy(
                                    name = name,
                                    quantity = quantity,
                                    category = selectedCategory,
                                    pickupDay = state.value.pickupDay,
                                    pickupTime = pickup_time
                                )
                                if (name.isNotEmpty() && quantity.isNotEmpty() && selectedCategory.isNotEmpty() && state.value.pickupDay.isNotEmpty() && pickup_time.isNotEmpty()) {
                                    mainViewModel.editFridgeItem(userId, item.itemId, updatedItem)
                                }
                            },
                            enabled = name.isNotEmpty() && quantity.isNotEmpty() && selectedCategory.isNotEmpty() && state.value.pickupDay.isNotEmpty() && pickup_time.isNotEmpty()
                        ) {
                            androidx.compose.material.Text(
                                text = "Confirm",
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        )
}

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopUp(
    mainViewModel: MainViewModel,
    userId: String,
    //pickupDay: String,
    //onPickupDayChange: (String) -> Unit,
) {
    val state = mainViewModel.mainViewState.collectAsState()
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    var name by remember {
        mutableStateOf((""))
    }
    var quantity by remember {
        mutableStateOf((""))
    }

    var pickup_time by remember {
        mutableStateOf((""))
    }

    var selectedCategory by remember { mutableStateOf("") }



    AlertDialog(
        containerColor = MaterialTheme.colorScheme.secondary,
        onDismissRequest = { mainViewModel.dismissDialog() },
        confirmButton = {},
        text = {
            Column(
            ) {
                // Name
                OutlinedTextField(
                    value = name,
                    onValueChange = { newText -> name = newText },
                    label = { androidx.compose.material.Text("Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    colors = getOutlinedTextFieldColors()
                )

                // Quantity
                OutlinedTextField(
                    value = quantity,
                    onValueChange = { newText -> quantity = newText },
                    label = { androidx.compose.material.Text("Quantity") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    colors = getOutlinedTextFieldColors()
                )

                // Category DropdownMenu
                CategoryDropdownMenu(
                    categories = mainViewModel.mainViewState.value.listOfCategories,
                    selectedCategory = selectedCategory,
                    onCategorySelected = { category ->
                        selectedCategory = category
                    },
                )

                // Pickup Date
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .background(MaterialTheme.colorScheme.primary),
                    onClick = {
                        val datePickerDialog = DatePickerDialog(
                            context,
                            { _, year, month, dayOfMonth ->
                                mainViewModel.setPickupDay("$dayOfMonth.${month + 1}.$year")
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        )
                        datePickerDialog.show()
                    }) {
                    Text(
                        text = if (state.value.pickupDay.isNotEmpty()) state.value.pickupDay else "Select Pickup Day",
                        color = Color.White // Set text color to match the outlinedTextField
                    )
                }


                // Pickup Time
                OutlinedTextField(
                    value = pickup_time,
                    onValueChange = { newText -> pickup_time = newText },
                    label = { androidx.compose.material.Text("Pick-up time") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    colors = getOutlinedTextFieldColors()
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 25.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { mainViewModel.dismissDialog() },
                        modifier = Modifier
                            .width(125.dp)
                            .height(60.dp)
                            .padding(top = 5.dp)
                            .shadow(
                                5.dp,
                                shape = RoundedCornerShape(20.dp),
                                ambientColor = MaterialTheme.colorScheme.onSecondary
                            )
                            .clip(RoundedCornerShape(20.dp)),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                        shape = RectangleShape,
                    ) {
                        androidx.compose.material.Text(
                            "Cancel",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 16.sp
                        )
                    }

                    Button(
                        modifier = Modifier
                            .width(125.dp)
                            .height(60.dp)
                            .padding(top = 5.dp)
                            .shadow(
                                5.dp,
                                shape = RoundedCornerShape(28.dp),
                                ambientColor = MaterialTheme.colorScheme.onSecondary

                            )
                            .clip(RoundedCornerShape(20.dp)),
                        shape = RectangleShape,
                        onClick = {
                            mainViewModel.dismissDialog()
                            mainViewModel.refreshOwnFridgeItemsNotReserved(userId)
                            val fridgeItem = FridgeItem(
                                name = name,
                                quantity = quantity,
                                category = selectedCategory,
                                pickupDay = state.value.pickupDay,
                                pickupTime = pickup_time
                            )
                            if (name.isNotEmpty() && quantity.isNotEmpty() && selectedCategory.isNotEmpty() && state.value.pickupDay.isNotEmpty() && pickup_time.isNotEmpty()) {
                                mainViewModel.addItemToFridge(userId, fridgeItem)
                            }
                        },
                        enabled = name.isNotEmpty() && quantity.isNotEmpty() && selectedCategory.isNotEmpty() && state.value.pickupDay.isNotEmpty() && pickup_time.isNotEmpty()
                    ) {
                        androidx.compose.material.Text(
                            text = "Confirm",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun CategoryDropdownMenu(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .padding(bottom = 8.dp)
            .background(MaterialTheme.colorScheme.primary),
    ) {
        // Display placeholder or selected category
        val displayText = if (selectedCategory.isNotEmpty()) selectedCategory else "Select a category"

        Text(
            text = displayText,
            modifier = Modifier.clickable { expanded = true }
                .padding(8.dp) // Add padding to center the text vertically
                .align(Alignment.CenterStart) // Align the text vertically in the center
            ,
            color = MaterialTheme.colorScheme.onPrimary

        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            // Add a placeholder item
            if (selectedCategory.isNotEmpty()) {
                DropdownMenuItem(
                    text = {},
                    onClick = {
                        onCategorySelected("")
                        expanded = false
                    }
                )
            }

            // Display categories
            categories.forEach { name ->
                DropdownMenuItem(
                    text = {
                        Text(text = name)
                    },
                    onClick = {
                        onCategorySelected(name)
                        expanded = false
                    }
                )
            }
        }
        Icon(
            Icons.Default.ArrowDropDown,
            tint = MaterialTheme.colorScheme.onPrimary,
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterEnd) // Align the icon to the right-center
                .clickable { expanded = true }
        )    }
}


data class ListModel(
    val categoryName: String,
    val categoryImg: Int
)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomListView(mainViewModel: MainViewModel) {
    val state = mainViewModel.mainViewState.collectAsState()

    lateinit var courseList: List<ListModel>
    courseList = ArrayList<ListModel>()

    courseList = courseList + ListModel("Vegetables", R.drawable.category_vegetables)
    courseList = courseList + ListModel("Fruits", R.drawable.category_fruits)
    courseList = courseList + ListModel("Meat", R.drawable.category_meat)
    courseList = courseList + ListModel("Seafood", R.drawable.category_seafood)
    courseList = courseList + ListModel("Leftovers", R.drawable.category_leftovers)
    courseList = courseList + ListModel("Dairy", R.drawable.category_dairy)
    courseList = courseList + ListModel("Sweets", R.drawable.category_sweets)
    courseList = courseList + ListModel("Grains", R.drawable.category_grains)
    courseList = courseList + ListModel("Beverages", R.drawable.category_beverages)

    LazyRow {
        itemsIndexed(courseList) { index, item ->
            Card(
                onClick = {
                    mainViewModel.toggleFilter(courseList[index].categoryName)
                    mainViewModel.toggleColors(index)
                },
                modifier = Modifier
                    .padding(8.dp)
                    .width(110.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.onPrimary),
                backgroundColor = state.value.cardColors[index],
                elevation = 6.dp
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(5.dp))

                    Icon(
                        painter = painterResource(id = courseList[index].categoryImg),
                        contentDescription = "img",
                        modifier = Modifier
                            .height(75.dp)
                            .width(75.dp)
                            .padding(1.dp),
                        tint = state.value.imageTints[index]
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = courseList[index].categoryName,
                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                        fontFamily = MaterialTheme.typography.bodySmall.fontFamily,
                        modifier = Modifier.padding(4.dp),
                        color = state.value.textColor[index],
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileDropdownMenu(
    mainViewModel: MainViewModel,
    onLogout: () -> Unit,
    userId: String
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            //.fillMaxSize()
            .height(75.dp)
            .width(50.dp)
            .wrapContentSize(Alignment.TopStart)
    ) {
        IconButton(onClick = { expanded = true }, modifier = Modifier.fillMaxSize()) {
            Icon(
                painter = painterResource(R.drawable.icon_menu),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(350.dp)
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(MaterialTheme.colorScheme.onPrimary)
        ) {
            DropdownMenuItem(
                text = { Text("Logout") },
                onClick = {
                    onLogout()
                    mainViewModel.setLoggedInUserId(null)
                    mainViewModel.selectScreen(Screen.Login)

                },
                leadingIcon = {
                    Icon(
                        Icons.Outlined.ExitToApp,
                        contentDescription = null
                    )
                })
        }
    }
}

@Composable
fun LocationDropdownMenu() {
    var expanded by remember { mutableStateOf(false) }
    var selectedCity by remember { mutableStateOf("St. Pölten") }

    Box(
        modifier = Modifier
            .height(65.dp)
            .width(350.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(onClick = { expanded = true }) {
                Text(
                    selectedCity,
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 36.sp,
                    textDecoration = TextDecoration.Underline
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary, // Customize the color if needed
                    modifier = Modifier.size(48.dp) // Adjust the size if needed
                )
            }
        }

        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.onPrimary)
                .offset(x = (-100).dp, y = 60.dp)
        ) {
            DropdownMenu(
                modifier = Modifier
                    .width(200.dp)
                    .heightIn(max = 300.dp),
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                val cities = listOf("St. Pölten", "Vienna", "Graz", "Linz", "Salzburg", "Innsbruck", "Klagenfurt", "Eisenstadt")

                cities.forEach { city ->
                    DropdownMenuItem(
                        text = { Text(city) },
                        onClick = {
                            selectedCity = city
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

