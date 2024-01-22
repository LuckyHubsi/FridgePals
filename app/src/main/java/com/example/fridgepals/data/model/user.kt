package com.example.fridgepals.data.model

data class User(
    val name: String = "",
    val email: String = "",
    val address: Address = Address(),
    val fridge: Map<String, FridgeItem> = emptyMap(),
    val reservations: Reservations = Reservations()
)

data class Address(
    val city: String = "",
    val street: String = ""
)

data class FridgeItem(
    var itemId: String = "",
    val ownerId: String = "",
    val name: String = "",
    val quantity: String = "",
    val category: String = "",
    val pickupDay: String = "",
    val pickupTime: String = "",
    val reserved: Boolean = false
)

data class Reservations(
    val reservationId: String = "",
    val offeringUserId: String = "",
    val itemId: String = ""
)
