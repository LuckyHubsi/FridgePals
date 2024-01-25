package com.example.fridgepals.ui.view_model

import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import com.example.fridgepals.data.model.FridgeItem
import com.example.fridgepals.data.model.Reservations
import com.example.fridgepals.ui.theme.colors
import com.example.fridgepals.ui.view.Screen

data class MainViewState (
    val selectedScreen: Screen = Screen.Login,
    val isUserLoggedIn: Boolean = false,
    val openDialog: Boolean = false,
    val openDialogEdit: Boolean = false,
    val openEditUser: Boolean = false,

    val userId: String = "",
    var currentItemToEdit: FridgeItem? = null,

    val communityFridgeItems: List<FridgeItem> = listOf(),
    val ownFridgeItemsNotReserved: List<FridgeItem> = listOf(),
    val ownFridgeItemsReserved: List<FridgeItem> = listOf(),
    val reservedItems: List<FridgeItem> = listOf(),
    val reservationsList: List<Reservations> = listOf(),
    val pickupDay: String = "",
    val listOfCategories: List<String> = listOf(), // List of category names

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

        // TODO: Anstatt state in place zu manipulieren, einen neuen state mit den änderungen machen (.copy) und returnen
        cardColors = cardColors.toMutableList().also { it[index] = newColorCard }
        imageTints = imageTints.toMutableList().also { it[index] = newColorImage }
        textColor = textColor.toMutableList().also { it[index] = newColorText }
    }
}
