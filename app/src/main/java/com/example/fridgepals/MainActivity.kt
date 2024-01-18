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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fridgepals.data.FirebaseManager
import com.example.fridgepals.data.model.Address
import com.example.fridgepals.data.model.User
import com.example.fridgepals.ui.theme.FridgePalsTheme
import com.example.fridgepals.ui.view.MainView
import com.example.fridgepals.ui.view.Login
import com.example.fridgepals.ui.view.Register
import com.example.fridgepals.ui.view.Screen
import com.example.fridgepals.ui.view_model.MainViewModel
import com.example.fridgepals.ui.view_model.MainViewState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val mainViewModel = MainViewModel()

        super.onCreate(savedInstanceState)
        setContent {
            FridgePalsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
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