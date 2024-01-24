package com.example.fridgepals.ui.view_model

import androidx.lifecycle.ViewModel
import com.example.fridgepals.data.model.FridgeItem
import com.example.fridgepals.repository.FridgeRepository
import com.example.fridgepals.ui.view.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
class MainViewModel() : ViewModel() {
    private val _mainViewState = MutableStateFlow(MainViewState())
    val mainViewState: StateFlow<MainViewState> = _mainViewState.asStateFlow()

    fun selectScreen(screen: Screen) {
        _mainViewState.update { it.copy(selectedScreen = screen) }
    }

    fun updateAuth(state: Boolean) {
        _mainViewState.update { it.copy(isUserLoggedIn = state) }
    }

    fun toggleColors(index: Int) {
        _mainViewState.update { it.copy().also { state -> state.toggleColors(index) } }
    }

    fun dismissDialog() {
        _mainViewState.update { it.copy(openDialog = false) }
    }

    fun openDialog() {
        _mainViewState.update { it.copy(openDialog = true) }
    }

    fun dismissDialogEdit() {
        _mainViewState.update { it.copy(openDialogEdit = false) }
    }

    fun openDialogEdit() {
        _mainViewState.update { it.copy(openDialogEdit = true) }
    }

    fun dismissEditUser() {
        _mainViewState.update { it.copy(openEditUser = false) }
    }

    fun openEditUser() {
        _mainViewState.update { it.copy(openEditUser = true) }
    }

    fun refreshOwnFridgeItemsNotReserved(userId: String) {
        FridgeRepository.getFridgeItemsNotReserved(userId,
            onSuccess = { items ->
                _mainViewState.value = mainViewState.value.copy(ownFridgeItemsNotReserved = items)
            },
            onFailure = { }
        )
    }

    fun refreshOwnFridgeItemsReserved(userId: String) {
        FridgeRepository.getFridgeItemsReserved(userId,
            onSuccess = { items ->
                _mainViewState.value = mainViewState.value.copy(ownFridgeItemsReserved = items)
            },
            onFailure = { }
        )
    }

    fun refreshCommunityItems(userId: String) {
        FridgeRepository.getCommunityFridge(
            currentUserId = userId,
            onSuccess = { items ->
                _mainViewState.value = mainViewState.value.copy(communityFridgeItems = items)
            },
            onFailure = { }
        )
    }

    fun refreshReservedItems(userId: String) {
        FridgeRepository.getReservedItems(userId,
            onSuccess = { items ->
                _mainViewState.value = mainViewState.value.copy(reservedItems = items)
            },
            onFailure = { }
        )
    }

    fun refreshReservationsList(userId: String) {
        FridgeRepository.getReservations(userId,
            onSuccess = { reservations ->
                _mainViewState.value = mainViewState.value.copy(reservationsList = reservations)
            },
            onFailure = { })
    }

    fun refreshListOfCategories(userId: String) {
        FridgeRepository.getFoodCategories(
            onSuccess = { categories ->
                _mainViewState.value = mainViewState.value.copy(listOfCategories = categories)
            },
            onFailure = { }
        )
    }

    fun addItemToFridge(userId: String, fridgeItem: FridgeItem) {
        FridgeRepository.addItemToFridge(userId, fridgeItem,
            onSuccess = {  },
            onFailure = {  }
        )
    }
}

