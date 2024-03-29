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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fridgepals.R
import com.example.fridgepals.data.model.FridgeItem
import com.example.fridgepals.data.model.Reservations
import com.example.fridgepals.repository.FridgeRepository
import com.example.fridgepals.repository.UserRepository
import com.example.fridgepals.ui.view_model.MainViewModel


sealed class Screen(val route: String) {
    object First : Screen("first")
    object Second : Screen("second")
    object Third : Screen("third")
    object Login : Screen("login")
    object Register : Screen("register")
}

@Composable
fun MainView(
    mainViewModel: MainViewModel,
    userId: String
) {
    val state = mainViewModel.mainViewState.collectAsState()
    val navController = rememberNavController()

    LaunchedEffect(userId) {
        mainViewModel.refreshOwnFridgeItemsNotReserved(userId)
    }
    LaunchedEffect(userId) {
        mainViewModel.refreshOwnFridgeItemsReserved(userId)
    }
    LaunchedEffect(userId) {
        mainViewModel.refreshCommunityItems(userId)
    }
    LaunchedEffect(userId) {
        mainViewModel.refreshReservedItems(userId)
    }
    LaunchedEffect(userId) {
        mainViewModel.refreshReservationsList(userId)
    }
    LaunchedEffect(userId) {
        mainViewModel.refreshListOfCategories(userId)
    }



    Scaffold(
        bottomBar = { BottomNavigationBar(navController, state.value.selectedScreen, mainViewModel, userId) }
    ) {
        NavHost(
            navController = navController,
            modifier = Modifier.padding(it),
            startDestination = Screen.First.route
        ) {
            composable(Screen.First.route) {
                mainViewModel.selectScreen(Screen.First)
                OwnFridge(
                    mainViewModel,
                    navController,
                    userId,
                    onDeleteItem = { item ->
                        FridgeRepository.deleteFridgeItem(
                            userId,
                            item.itemId,
                            onSuccess = {
                                FridgeRepository.getFridgeItemsNotReserved(
                                    userId,
                                    onSuccess = {
                                        mainViewModel.refreshOwnFridgeItemsNotReserved(userId)
                                        mainViewModel.refreshOwnFridgeItemsReserved(userId)
                                                },
                                    onFailure = {  })
                            },
                            onFailure = {
                            })
                    },
                    onEditItem = {item ->
                        mainViewModel.setCurrentItemToEdit(item)
                    },
                )
            }
            composable(Screen.Second.route) {
                mainViewModel.selectScreen(Screen.Second)
                CommunityFridge(mainViewModel, onReserve = { offeringUserId, itemId ->
                    val reservingUserId = userId
                    FridgeRepository.reserveItem(
                        reservingUserId,
                        offeringUserId,
                        itemId,
                        {
                            mainViewModel.refreshCommunityItems(userId)
                            mainViewModel.refreshReservedItems(userId)
                        },
                        {})})
                mainViewModel.refreshCommunityItems(userId)
                mainViewModel.refreshReservedItems(userId)
            }
            composable(Screen.Third.route) {
                mainViewModel.selectScreen(Screen.Third)
                ReservedItems(
                    mainViewModel,
                            onCancel = { reservationId ->
                        FridgeRepository.deleteReservation(userId, reservationId, onSuccess = {
                            mainViewModel.refreshReservedItems(userId)
                            mainViewModel.refreshReservationsList(userId)
                        }, onFailure = {})
                    }
                )
                mainViewModel.refreshReservedItems(userId)
                mainViewModel.refreshReservationsList(userId)
            }
            composable(Screen.Login.route) {
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
            composable(Screen.Register.route) {
                mainViewModel.selectScreen(Screen.Register)
                Register(mainViewModel,onRegistrationComplete = { email, password, user ->
                    UserRepository.registerUser(email, password, user,
                        onSuccess = {
                        },
                        onFailure = {
                        })
                })
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController, selectedScreen: Screen, mainViewModel: MainViewModel, userId: String) {
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
            onClick = {
                navController.navigate(Screen.First.route)
                mainViewModel.refreshOwnFridgeItemsNotReserved(userId)
                mainViewModel.refreshOwnFridgeItemsReserved(userId)
                      },
            icon = R.drawable.icon_fridge,
            modifier = Modifier.width(itemWidth)
        )

        BottomNavItem(
            selected = (selectedScreen == Screen.Second),
            onClick = {
                navController.navigate(Screen.Second.route)
                mainViewModel.refreshCommunityItems(userId)
                      },
            icon = R.drawable.icon_search,
            modifier = Modifier.width(itemWidth)
        )

        BottomNavItem(
            selected = (selectedScreen == Screen.Third),
            onClick = {
                navController.navigate(Screen.Third.route)
                mainViewModel.refreshReservedItems(userId)
                      },
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
