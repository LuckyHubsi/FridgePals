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
        fridgeItem.itemId = itemRef.key ?: return onFailure("Failed to generate item ID")

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

    fun getFridgeItemsNotReserved(userId: String, onSuccess: (List<FridgeItem>) -> Unit, onFailure: (String) -> Unit) {
        val fridgeRef = FirebaseManager.database.reference.child("users").child(userId).child("fridge")
        fridgeRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = snapshot.children.mapNotNull { dataSnapshot ->
                    val item = dataSnapshot.getValue(FridgeItem::class.java)
                    // Only add the item if 'reserved' is true
                    if (item != null && !item.reserved) item else null }
                onSuccess(items)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                onFailure(databaseError.message)
            }
        })
    }

    fun getFridgeItemsReserved(userId: String, onSuccess: (List<FridgeItem>) -> Unit, onFailure: (String) -> Unit) {
        val fridgeRef = FirebaseManager.database.reference.child("users").child(userId).child("fridge")
        fridgeRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = snapshot.children.mapNotNull { dataSnapshot ->
                    val item = dataSnapshot.getValue(FridgeItem::class.java)
                    // Only add the item if 'reserved' is true
                    if (item != null && item.reserved) item else null }
                onSuccess(items)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                onFailure(databaseError.message)
            }
        })
    }

    fun editFridgeItem(userId: String, itemId: String, updateItem: FridgeItem, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val itemRef = FirebaseManager.database.reference.child("users").child(userId).child("fridge").child(itemId)
        itemRef.setValue(updateItem)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e.message ?: "Failed to update item") }
    }

    fun deleteFridgeItem(userId: String, itemId: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val itemRef = FirebaseManager.database.reference.child("users").child(userId).child("fridge").child(itemId)
        itemRef.removeValue()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e.message ?: "Failed to delete item") }
    }

    fun getCommunityFridge(onSuccess: (List<FridgeItem>) -> Unit, onFailure: (String) -> Unit) {
        val usersRef = FirebaseManager.database.reference.child("users")
        val allFridgeItems = mutableListOf<FridgeItem>()

        usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach { userSnapshot ->
                    userSnapshot.child("fridge").children.forEach { itemSnapshot ->
                        val item = itemSnapshot.getValue(FridgeItem::class.java)
                        // Check if the item is not reserved before adding it
                        if (item != null && !item.reserved) {
                            allFridgeItems.add(item)
                        }
                    }
                }
                onSuccess(allFridgeItems)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                onFailure(databaseError.message)
            }
        })
    }

}
