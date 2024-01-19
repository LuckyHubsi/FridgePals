package com.example.fridgepals.ui.view


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fridgepals.R
import com.example.fridgepals.ui.view_model.MainViewModel

@Composable
fun RoundedCard(
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    onEditClick: () -> Unit = {},
    onRemoveClick: () -> Unit = {},
    buttonContent: @Composable () -> Unit
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
                imageVector = Icons.Default.Email,
                contentDescription = null,
                modifier = imageModifier
                    .size(85.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Gray)
                    .padding(8.dp)
            )

            // TextBlock
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 8.dp)
            ) {
                androidx.compose.material3.Text(
                    "Item Name",
                    style = MaterialTheme.typography.bodyLarge,
                )
                androidx.compose.material3.Text(
                    "Location",
                    style = MaterialTheme.typography.bodyMedium,
                )
                androidx.compose.material3.Text(
                    "Date",
                    style = MaterialTheme.typography.bodyMedium,
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
fun ButtonContentOwnFridge(mainViewModel: MainViewModel) {
    // Second row: Edit and Remove buttons
    Button(
        onClick = { },
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
            "Remove",
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 22.sp
        )
    }

    Button(
        onClick = { mainViewModel.openDialog() },
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
            fontSize = 22.sp
        )
    }
}

@Composable
fun ButtonContentCommunityFridge() {
    Button(
        onClick = { },
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
            fontSize = 22.sp
        )
    }
}

@Composable
fun ButtonContentReservedItems() {
    Button(
        onClick = { },
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
            fontSize = 22.sp
        )
    }
}

@Composable
fun PopUp(mainViewModel: MainViewModel) {
    val state = mainViewModel.mainViewState.collectAsState()

    var name by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }
    var quantity by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }
    var category by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }
    var pickup_date by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }
    var pickup_time by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }

    if (state.value.openDialog)
        AlertDialog(
            containerColor = MaterialTheme.colorScheme.secondary,
            onDismissRequest = { mainViewModel.dismissDialog() },
            confirmButton = {},
            text = {
                Column(
                ) {
                    // Name TextField
                    OutlinedTextField(
                        value = name,
                        onValueChange = { newText -> name = newText },
                        label = { androidx.compose.material.Text("Name") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        colors = getOutlinedTextFieldColors()
                    )

                    // City TextField
                    OutlinedTextField(
                        value = quantity,
                        onValueChange = { newText -> quantity = newText },
                        label = { androidx.compose.material.Text("Quantity") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        colors = getOutlinedTextFieldColors()
                    )

                    // Street TextField
                    OutlinedTextField(
                        value = category,
                        onValueChange = { newText -> category = newText },
                        label = { androidx.compose.material.Text("Category") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        colors = getOutlinedTextFieldColors()
                    )

                    // Email TextField
                    OutlinedTextField(
                        value = pickup_date,
                        onValueChange = { newText -> pickup_date = newText },
                        label = { androidx.compose.material.Text("Pick-up date") },
/*                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = null
                            )
                        },*/
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                        colors = getOutlinedTextFieldColors()
                    )

                    // Password TextField
                    OutlinedTextField(
                        value = pickup_time,
                        onValueChange = { newText -> pickup_time = newText },
                        label = { androidx.compose.material.Text("Pick-up time") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        visualTransformation = PasswordVisualTransformation(),
                        colors = getOutlinedTextFieldColors()
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 25.dp)
                        ,
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
                                .clip(RoundedCornerShape(20.dp))
                            ,
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                            shape = RectangleShape,
                        ) {
                            androidx.compose.material.Text(
                                "Cancel",
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 20.sp
                            )
                        }


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
                            shape = RectangleShape
                        ) {
                            androidx.compose.material.Text(
                                "Confirm",
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 20.sp
                            )
                        }
                    }
                }




            }
            )
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
                    mainViewModel.toggleColors(index)
                },

                modifier = Modifier
                    .padding(8.dp)
                    .width(110.dp),

                backgroundColor = state.value.cardColors[index],

                elevation = 6.dp
            )
            {
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


