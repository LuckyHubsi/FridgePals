package com.example.fridgepals.ui.view_model

import com.example.fridgepals.ui.view.Screen

data class MainViewState (
    val selectedScreen: Screen = Screen.Login,
    var authenticator: Boolean = true,
)