package com.example.fridgepals.repository

import com.example.fridgepals.data.FirebaseManager
import com.example.fridgepals.data.model.FridgeItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

object FridgeRepository {
    fun addItemToFridge(userId: String, fridgeItem: FridgeItem, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
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

    fun getFoodCategories(onSuccess: (List<String>) -> Unit, onFailure: (String) -> Unit){
        val categoriesRef = FirebaseManager.database.reference.child("foodCategories")
        categoriesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val categories = snapshot.getValue<List<String>>() ?: return onFailure("No categories found")
                onSuccess(categories)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                onFailure(databaseError.message)
            }
        })
    }

    fun getFridgeItems(userId: String, onSuccess: (List<FridgeItem>) -> Unit, onFailure: (String) -> Unit) {
        val fridgeRef = FirebaseManager.database.reference.child("users").child(userId).child("fridge")
        fridgeRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = snapshot.children.mapNotNull { it.getValue(FridgeItem::class.java) }
                onSuccess(items)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                onFailure(databaseError.message)
            }
        })
    }

}
