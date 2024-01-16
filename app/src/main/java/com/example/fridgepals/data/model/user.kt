package com.example.fridgepals.data.model

data class User(
    val name: String,
    val email: String,
    val address: Address,
    val virtualFridgeId: String,
    val reservedItems: List<String>
)
 {

}
data class Address(
    val city: String,
    val street: String
)
