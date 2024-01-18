package com.example.fridgepals.ui.view


import android.graphics.drawable.VectorDrawable
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.Key.Companion.I
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fridgepals.ui.view_model.MainViewModel
import com.example.fridgepals.R
import com.example.fridgepals.ui.theme.colors

sealed class Screen(val route: String){
    object First: Screen("first")
    object Second: Screen("second")
    object Third: Screen("third")
    object Login: Screen("login")
    object Register: Screen("register")
}

@Composable
fun MainView(mainViewModel: MainViewModel){
    val state = mainViewModel.mainViewState.collectAsState()
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController, state.value.selectedScreen) }
    ) {
        NavHost(
            navController = navController,
            modifier = Modifier.padding(it),
            startDestination = Screen.First.route
        ){
            composable(Screen.First.route){
                mainViewModel.selectScreen(Screen.First)
                OwnFridge(mainViewModel)
            }
            composable(Screen.Second.route){
                mainViewModel.selectScreen(Screen.Second)
                CommunityFridge(mainViewModel)
            }
            composable(Screen.Third.route){
                mainViewModel.selectScreen(Screen.Third)
                ReservedItems(mainViewModel)
            }
            composable(Screen.Login.route){
                mainViewModel.selectScreen(Screen.Login)
                Login(mainViewModel)
            }
            composable(Screen.Register.route){
                mainViewModel.selectScreen(Screen.Register)
                Register(mainViewModel)
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController, selectedScreen: Screen) {
    val itemCount = 3 // Number of items in the BottomNavigationBar
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    val itemWidth = (screenWidthDp / itemCount).dp

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(MaterialTheme.colorScheme.primary)
    ) {
        BottomNavItem(
            selected = (selectedScreen == Screen.First),
            onClick = { navController.navigate(Screen.First.route) },
            icon = R.drawable.icon_fridge,
            modifier = Modifier.width(itemWidth)
        )

        BottomNavItem(
            selected = (selectedScreen == Screen.Second),
            onClick = { navController.navigate(Screen.Second.route) },
            icon = R.drawable.icon_search,
            modifier = Modifier.width(itemWidth)
        )

        BottomNavItem(
            selected = (selectedScreen == Screen.Third),
            onClick = { navController.navigate(Screen.Third.route) },
            icon = R.drawable.icon_basket,
            modifier = Modifier.width(itemWidth)
        )
    }
}

@Composable
fun BottomNavItem(selected: Boolean, onClick: () -> Unit, icon: Int, modifier: Modifier) {
    Box(
        modifier = modifier
            .clickable(onClick = onClick)
            .background(
                if (selected) {
                    MaterialTheme.colorScheme.tertiary
                } else {
                    MaterialTheme.colorScheme.primary
                }
            )
            .drawBehind {
                if (selected) {
                    val borderSize = 3.dp.toPx()
                    val y = size.height - borderSize / 2
                    drawLine(
                        color = Color.White,
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = borderSize
                    )
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(icon),
            modifier = Modifier
                .padding(14.dp)
                .size(42.dp),
            contentDescription = "",
            tint = if (selected) Color.White else MaterialTheme.colorScheme.onPrimary
        )
    }
}


@Composable
fun RoundedCard(
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    onEditClick: () -> Unit = {},
    onRemoveClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(10.dp)
            .shadow(
                5.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = MaterialTheme.colorScheme.onSecondary
            )
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.secondary)
    ) {
        // Top-left image
        Icon(
            imageVector = Icons.Default.Email,
            contentDescription = null,
            modifier = imageModifier
                .size(85.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.Gray)
                .padding(8.dp)
        )

        // Texts and buttons
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            androidx.compose.material3.Text(
                "Item Name",
                style = MaterialTheme.typography.titleMedium,
            )
            androidx.compose.material3.Text(
                "Description",
                style = MaterialTheme.typography.titleSmall,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Edit button
                TextButton(onClick = { onEditClick() }) {
                    androidx.compose.material3.Text("Remove")
                }

                // Remove button
                TextButton(onClick = { onRemoveClick() }) {
                    androidx.compose.material3.Text("Edit")
                }
            }
        }
    }
}


// in the below line, we are creating a
// model class for our data class.
data class ListModel(

    // in the below line, we are creating two variable
    // one is for language name which is string.
    val languageName: String,

    // next variable is for our
    // image which is an integer.
    val languageImg: Int
)


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun customListView() {
    // initializing our array list
    lateinit var courseList: List<ListModel>
    courseList = ArrayList<ListModel>()

    // in the below line, we are adding data to our list.
    courseList = courseList + ListModel("Vegetables", R.drawable.category_vegetables)
    courseList = courseList + ListModel("Fruits", R.drawable.category_fruits)
    courseList = courseList + ListModel("Meat", R.drawable.category_meat)
    courseList = courseList + ListModel("Seafood", R.drawable.category_seafood)
    courseList = courseList + ListModel("Leftovers", R.drawable.category_leftovers)
    courseList = courseList + ListModel("Dairy", R.drawable.category_dairy)
    courseList = courseList + ListModel("Sweets", R.drawable.category_sweets)
    courseList = courseList + ListModel("Grains", R.drawable.category_grains)
    courseList = courseList + ListModel("Beverages", R.drawable.category_beverages)

    val cardColors = remember { mutableStateListOf(*Array(courseList.size) { colors.NotQuiteWhite }) }

    val imageTints = remember { mutableStateListOf(*Array(courseList.size) { colors.GreenBlue }) }

    val textColors = remember { mutableStateListOf(*Array(courseList.size) { colors.GreenBlue }) }

    // in the below line, we are creating a
    // lazy row for displaying a horizontal list view.
    LazyRow {
        // in the below line, we are setting data for each item of our listview.
        itemsIndexed(courseList) { index, item ->
            // in the below line, we are creating a card for our list view item.
            Card(
                // inside our grid view on below line
                // we are adding on click for each item of our grid view.
                onClick = {
                    // inside on click we are displaying the toast message.
                    // Toggle the color of the clicked card
                    cardColors[index] =
                        if (cardColors[index] == colors.NotQuiteWhite) colors.GreenBlue else colors.NotQuiteWhite

                    // Set the image tint color to White after clicking
                    imageTints[index] =
                        if (imageTints[index] == colors.GreenBlue) colors.NotQuiteWhite else colors.GreenBlue

                    textColors[index] =
                        if (textColors[index] == colors.GreenBlue) colors.NotQuiteWhite else colors.GreenBlue
                },
                // in the below line, we are adding
                // padding from our all sides.
                modifier = Modifier
                    .padding(8.dp)
                    .width(120.dp)
                ,

                backgroundColor = cardColors[index],

                // in the below line, we are adding
                // elevation for the card.
                elevation = 6.dp
            )
            {
                // in the below line, we are creating
                // a row for our list view item.
                Column(
                    // for our row we are adding modifier
                    // to set padding from all sides.
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // in the below line, inside row we are adding spacer
                    Spacer(modifier = Modifier.height(5.dp))

                    // in the below line, we are adding Image to display the image.
                    Icon(
                        // in the below line, we are specifying the drawable image for our image.
                        painter = painterResource(id = courseList[index].languageImg),

                        // in the below line, we are specifying
                        // content description for our image
                        contentDescription = "img",

                        // in the below line, we are setting height
                        // and width for our image.
                        modifier = Modifier
                            .height(75.dp)
                            .width(75.dp)
                            .padding(1.dp)
                        ,

                        //alignment = Alignment.Center,

                        tint = imageTints[index]
                        )

                    // in the below line, we are adding spacer between image and a text
                    Spacer(modifier = Modifier.height(5.dp))

                    // in the below line, we are creating a text.
                    Text(
                        // inside the text on below line we are
                        // setting text as the language name
                        // from our model class.
                        text = courseList[index].languageName,

                        // in the below line, we are adding padding
                        // for our text from all sides.
                        modifier = Modifier.padding(4.dp),

                        // in the below line, we are adding color for our text
                        color = textColors[index],

                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}


