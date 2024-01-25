package com.example.fridgepals.ui.view_model

import androidx.compose.runtime.mutableStateListOf
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
    val selectedFilters = mutableStateListOf<String>()

    fun selectScreen(screen: Screen) {
        _mainViewState.update { it.copy(selectedScreen = screen) }
    }

    fun toggleColors(index: Int) {
        _mainViewState.update { it.copy().also { state -> state.toggleColors(index) } }
    }

    fun setLoggedInUserId(userId: String?) {
        _mainViewState.update { it.copy(userId = userId) }
    }

    fun dismissDialog() {
        _mainViewState.update { it.copy(openDialog = false) }
    }

    fun openDialog() {
        _mainViewState.update { it.copy(openDialog = true) }
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
            onSuccess = { },
            onFailure = { }
        )
    }


    // Function to edit a fridge item
    fun editFridgeItem(userId: String, itemId: String, updatedItem: FridgeItem) {
        FridgeRepository.editFridgeItem(userId, itemId, updatedItem,
            onSuccess = {
                // Handle the success case, e.g., by refreshing the list of items
                refreshOwnFridgeItemsReserved(userId)
                refreshOwnFridgeItemsNotReserved(userId)
            },
            onFailure = {
                // Handle the failure case
            }
        )
        setCurrentItemToEdit(null)
    }

    fun setCurrentItemToEdit(item: FridgeItem?) {
        _mainViewState.update { currentState ->
            currentState.copy(currentItemToEdit = item)
        }
    }


    // Function to toggle a filter
    fun toggleFilter(filter: String) {
        if (selectedFilters.contains(filter)) {
            selectedFilters.remove(filter)
        } else {
            selectedFilters.add(filter)
        }
    }
}

