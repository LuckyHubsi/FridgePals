package com.example.fridgepals.repository

import com.example.fridgepals.data.FirebaseManager
import com.example.fridgepals.data.model.FridgeItem
import com.google.firebase.auth.FirebaseAuth

object FridgeRepository {
    fun addItemToFridge(fridgeItem: FridgeItem, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        // Get the UID of the currently signed-in user
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return onFailure("User not logged in")

        // Reference to the user's fridge in the database
        val itemRef = FirebaseManager.database.reference.child("users").child(userId).child("fridge").push()

        // Set the value of the new item in the user's fridge
        itemRef.setValue(fridgeItem)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e ->
                onFailure(e.message ?: "Failed to add item to fridge")
            }
    }
}
