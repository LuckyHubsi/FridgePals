package com.example.fridgepals.ui.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fridgepals.ui.view.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(): ViewModel() {
    private val _mainViewState = MutableStateFlow(MainViewState())
    val mainViewState: StateFlow<MainViewState> = _mainViewState.asStateFlow()

    fun selectScreen(screen: Screen){
        _mainViewState.update { it.copy(selectedScreen = screen) }
    }

    fun updateAuth() {
        _mainViewState.update { it.copy(authenticator = true) }
    }

    fun toggleColors(index: Int) {
        _mainViewState.update { it.copy().also { state -> state.toggleColors(index) } }
    }

    fun dismissDialog(){
        _mainViewState.update { it.copy(openDialog = false) }
    }

    fun openDialog(){
        _mainViewState.update { it.copy(openDialog = true) }
    }

    fun dismissEditUser(){
        _mainViewState.update { it.copy(openEditUser = false) }
    }

    fun openEditUser(){
        _mainViewState.update { it.copy(openEditUser = true) }
    }


}