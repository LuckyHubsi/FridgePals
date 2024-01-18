package com.example.fridgepals.ui.view


import android.graphics.drawable.VectorDrawable
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fridgepals.ui.view_model.MainViewModel
import com.example.fridgepals.R

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
                .size(40.dp)
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
                style = MaterialTheme.typography.titleMedium
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
                    androidx.compose.material3.Text("Edit")
                }

                // Remove button
                TextButton(onClick = { onRemoveClick() }) {
                    androidx.compose.material3.Text("Remove")
                }
            }
        }
    }
}


