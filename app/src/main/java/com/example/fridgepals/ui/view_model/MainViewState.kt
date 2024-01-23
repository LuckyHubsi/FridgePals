package com.example.fridgepals.ui.view_model

import androidx.compose.ui.graphics.Color
import com.example.fridgepals.ui.theme.colors
import com.example.fridgepals.ui.view.Screen

data class MainViewState (
    val selectedScreen: Screen = Screen.Login,
    val isUserLoggedIn: Boolean = false,
    val openDialog: Boolean = false,
    val openEditUser: Boolean = false,
    var categories: List<String> = listOf(), // List of category names
    var selectedCategory: String = "", // selected category name
    var cardColors: List<Color> = List(9) { colors.NotQuiteWhite },
    var imageTints: List<Color> = List(9) { colors.GreenBlue },
    var textColor: List<Color> = List(9) { colors.GreenBlue }
){
    // Function to update card colors, image tints, and text colors
    fun toggleColors(index: Int) {
        val newColorCard = if (cardColors[index] == colors.NotQuiteWhite) colors.GreenBlue else colors.NotQuiteWhite
        val newColorImage = if (imageTints[index] == colors.NotQuiteWhite) colors.GreenBlue else colors.NotQuiteWhite
        val newColorText = if (textColor[index] == colors.NotQuiteWhite) colors.GreenBlue else colors.NotQuiteWhite

        cardColors = cardColors.toMutableList().also { it[index] = newColorCard }
        imageTints = imageTints.toMutableList().also { it[index] = newColorImage }
        textColor = textColor.toMutableList().also { it[index] = newColorText }
    }
}
