package com.example.fridgepals.ui.view


import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fridgepals.ui.view_model.MainViewModel

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
            icon = Icons.Default.Home,
            modifier = Modifier.width(itemWidth)
        )

        BottomNavItem(
            selected = (selectedScreen == Screen.Second),
            onClick = { navController.navigate(Screen.Second.route) },
            icon = Icons.Default.Face,
            modifier = Modifier.width(itemWidth)
        )

        BottomNavItem(
            selected = (selectedScreen == Screen.Third),
            onClick = { navController.navigate(Screen.Third.route) },
            icon = Icons.Default.List,
            modifier = Modifier.width(itemWidth)
        )
    }
}

@Composable
fun BottomNavItem(selected: Boolean, onClick: () -> Unit, icon: ImageVector, modifier: Modifier) {
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
            modifier = Modifier
                .padding(8.dp)
                .size(48.dp),
            imageVector = icon,
            contentDescription = "",
            tint = if (selected) Color.White else MaterialTheme.colorScheme.onPrimary
        )
    }
}



