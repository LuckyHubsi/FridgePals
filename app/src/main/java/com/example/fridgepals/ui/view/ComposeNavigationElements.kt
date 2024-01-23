package com.example.fridgepals.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fridgepals.R
import com.example.fridgepals.data.model.FridgeItem
import com.example.fridgepals.data.model.Reservations
import com.example.fridgepals.repository.UserRepository
import com.example.fridgepals.ui.view_model.MainViewModel


sealed class Screen(val route: String){
    object First: Screen("first")
    object Second: Screen("second")
    object Third: Screen("third")
    object Login: Screen("login")
    object Register: Screen("register")
}

@Composable
fun MainView(mainViewModel: MainViewModel, communityFridgeItems: List<FridgeItem>, ownFridgeItemsNotReserved: List<FridgeItem>, ownFridgeItemsReserved: List<FridgeItem>, reservedItems: List<FridgeItem>, reservationsList: List<Reservations>){
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
                OwnFridge(mainViewModel, navController, ownFridgeItemsNotReserved, ownFridgeItemsReserved)
            }
            composable(Screen.Second.route){
                mainViewModel.selectScreen(Screen.Second)
                CommunityFridge(mainViewModel, communityFridgeItems)
            }
            composable(Screen.Third.route){
                mainViewModel.selectScreen(Screen.Third)
                ReservedItems(mainViewModel, reservedItems, reservationsList)
            }
            composable(Screen.Login.route){
                mainViewModel.selectScreen(Screen.Login)
                Login(mainViewModel, onLoginComplete = { email, password ->
                    UserRepository.loginUser(email, password,
                        onSuccess = {
                        },
                        onFailure = {
                        }
                    )
                })
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
