package com.example.fridgepals.ui.view_model

data class MainViewState (
    var isUserLoggedIn: Boolean = false,
    var categories: List<String> = listOf(), // List of category names
    var selectedCategory: String = "" // selected category name
)
