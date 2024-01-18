package com.example.fridgepals.data.model

data class User(
    val name: String = "",
    val email: String = "",
    val address: Address = Address(),
    val fridge: Map<String, FridgeItem> = emptyMap()
)

data class Address(
    val city: String = "",
    val street: String = ""
)

data class FridgeItem(
    val name: String = "",
    val quantity: String = "",
    val category: String = "",
    val pickupDay: String = "",
    val pickupTime: String = "",
    val reserved: Boolean = false
)
