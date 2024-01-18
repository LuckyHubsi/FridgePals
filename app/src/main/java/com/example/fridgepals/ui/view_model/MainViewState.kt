package com.example.fridgepals.ui.view_model

import androidx.compose.ui.graphics.Color
import com.example.fridgepals.ui.theme.colors
import com.example.fridgepals.ui.view.Screen

data class MainViewState (
    val selectedScreen: Screen = Screen.Login,
    var authenticator: Boolean = true,
    var cardColors: List<Color> = List(9) { colors.NotQuiteWhite },
    var imageTints: List<Color> = List(9) { colors.GreenBlue },
    var textColor: List<Color> = List(9) { colors.GreenBlue }
){
    // Function to update card colors, image tints, and text colors
    fun toggleColors(index: Int) {
        val newColor = if (cardColors[index] == colors.NotQuiteWhite) colors.GreenBlue else colors.NotQuiteWhite
        cardColors = cardColors.toMutableList().also { it[index] = newColor }
        imageTints = imageTints.toMutableList().also { it[index] = colors.NotQuiteWhite }
        textColor = textColor.toMutableList().also { it[index] = colors.NotQuiteWhite }
    }
}