package com.example.fridgepals.repository

import com.example.fridgepals.data.FirebaseManager
import com.example.fridgepals.data.model.FridgeItem
import com.example.fridgepals.data.model.Reservations
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

object FridgeRepository {
    fun addItemToFridge(
        userId: String,
        fridgeItem: FridgeItem,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        // Reference to the user's fridge in the database
        val itemRef =
            FirebaseManager.database.reference.child("users").child(userId).child("fridge").push()
        // copy of fridgeItem with the itemId and ownerId (for reservations needed)
        val fridgeItemWithItemAndUserId = fridgeItem.copy(
            itemId = itemRef.key ?: return onFailure("Failed to generate item ID"),
            ownerId = userId
        )


        // Set the value of the new item in the user's fridge
        itemRef.setValue(fridgeItemWithItemAndUserId)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e ->
                onFailure(e.message ?: "Failed to add item to fridge")
            }
    }

    fun getFoodCategories(onSuccess: (List<String>) -> Unit, onFailure: (String) -> Unit) {
        val categoriesRef = FirebaseManager.database.reference.child("foodCategories")
        categoriesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val categories =
                    snapshot.getValue<List<String>>() ?: return onFailure("No categories found")
                onSuccess(categories)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                onFailure(databaseError.message)
            }
        })
    }

    fun getFridgeItemsNotReserved(
        userId: String,
        onSuccess: (List<FridgeItem>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val fridgeRef =
            FirebaseManager.database.reference.child("users").child(userId).child("fridge")
        fridgeRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = snapshot.children.mapNotNull { dataSnapshot ->
                    val item = dataSnapshot.getValue(FridgeItem::class.java)
                    // Only add the item if 'reserved' is true
                    if (item != null && !item.reserved) item else null
                }
                onSuccess(items)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                onFailure(databaseError.message)
            }
        })
    }

    fun getFridgeItemsReserved(
        userId: String,
        onSuccess: (List<FridgeItem>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val fridgeRef =
            FirebaseManager.database.reference.child("users").child(userId).child("fridge")
        fridgeRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = snapshot.children.mapNotNull { dataSnapshot ->
                    val item = dataSnapshot.getValue(FridgeItem::class.java)
                    // Only add the item if 'reserved' is true
                    if (item != null && item.reserved) item else null
                }
                onSuccess(items)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                onFailure(databaseError.message)
            }
        })
    }

    fun editFridgeItem(
        userId: String,
        itemId: String,
        updateItem: FridgeItem,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val itemRef =
            FirebaseManager.database.reference.child("users").child(userId).child("fridge")
                .child(itemId)
        itemRef.setValue(updateItem)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e.message ?: "Failed to update item") }
    }

    fun deleteFridgeItem(
        userId: String,
        itemId: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val itemRef =
            FirebaseManager.database.reference.child("users").child(userId).child("fridge")
                .child(itemId)
        itemRef.removeValue()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e.message ?: "Failed to delete item") }
    }

    fun getCommunityFridge(
        currentUserId: String,
        onSuccess: (List<FridgeItem>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val usersRef = FirebaseManager.database.reference.child("users")
        val allFridgeItems = mutableListOf<FridgeItem>()

        usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach { userSnapshot ->

                    // Check if the current userSnapshot is not the signed-in user
                    if (userSnapshot.key != currentUserId) {
                        userSnapshot.child("fridge").children.forEach { itemSnapshot ->
                            val item = itemSnapshot.getValue(FridgeItem::class.java)
                            // Check if the item is not reserved before adding it
                            if (item != null && !item.reserved) {
                                allFridgeItems.add(item)
                            }
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

    fun reserveItem(
        reservingUserId: String,
        offeringUserId: String,
        itemId: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        // push a new reservation
        val reservationRef =
            FirebaseManager.database.reference.child("users").child(reservingUserId)
                .child("reservations").push()

        // create a reservation object with the autogenerated key
        val reservationId = reservationRef.key
        if (reservationId == null) {
            onFailure("Failed to generate reservation ID")
            return
        }

        val reservation = Reservations(reservationId, offeringUserId, itemId)
        reservationRef.setValue(reservation)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e.message ?: "Failed to reserve item") }

        // Mark the item as reserved in the offering user's fridge
        val itemRef =
            FirebaseManager.database.reference.child("users").child(offeringUserId).child("fridge")
                .child(itemId)
        itemRef.updateChildren(mapOf("reserved" to true))
            .addOnFailureListener { e -> /* Handle failure to mark item as reserved */ }
    }

    fun getReservedItems(
        userId: String,
        onSuccess: (List<FridgeItem>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val reservationsRef =
            FirebaseManager.database.reference.child("users").child(userId).child("reservations")
        val reservedItems = mutableListOf<FridgeItem>()

        reservationsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(reservationsSnapshot: DataSnapshot) {
                val totalReservations = reservationsSnapshot.childrenCount
                var processedReservations =
                    0 // needed to compare when all reservations have been run

                if (totalReservations == 0L) {
                    onSuccess(emptyList()) // no reservations, return empty list
                    return
                }

                reservationsSnapshot.children.forEach { reservationSnapshot ->
                    val reservation = reservationSnapshot.getValue(Reservations::class.java)
                    if (reservation != null) {
                        val itemRef = FirebaseManager.database.reference.child("users")
                            .child(reservation.offeringUserId).child("fridge")
                            .child(reservation.itemId)
                        itemRef.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(itemSnapshot: DataSnapshot) {
                                val item = itemSnapshot.getValue(FridgeItem::class.java)
                                item?.let { reservedItems.add(it) }

                                processedReservations++
                                if (processedReservations.toLong() == totalReservations) {
                                    onSuccess(reservedItems) // all items are processed, return the list
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                onFailure(error.message)
                                processedReservations++
                                if (processedReservations.toLong() == totalReservations) {
                                    onSuccess(reservedItems) // Return what's been processed so far
                                }
                            }
                        })
                    } else {
                        processedReservations++
                        if (processedReservations.toLong() == totalReservations) {
                            onSuccess(reservedItems) // All items processed, return the list
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                onFailure(databaseError.message)
            }
        })
    }

    fun getReservations(
        userId: String,
        onSuccess: (List<Reservations>) -> Unit,
        onFailure: (String) -> Unit) {
        val reservationsRef = FirebaseManager.database.reference.child("users").child(userId).child("reservations")
        val reservationsList = mutableListOf<Reservations>()

        reservationsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach { dataSnapshot ->
                    dataSnapshot.getValue(Reservations::class.java)?.let {
                        reservationsList.add(it)
                    }
                }
                if (reservationsList.isEmpty()) {
                    onFailure("No reservations found")
                } else {
                    onSuccess(reservationsList)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                onFailure(databaseError.message)
            }
        })
    }

    fun deleteReservation(
        userId: String,
        reservationId: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        // Reference to the user's reservation
        val reservationRef =
            FirebaseManager.database.reference.child("users").child(userId).child("reservations")
                .child(reservationId)

        // Fetch the reservation details
        reservationRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val reservation = snapshot.getValue(Reservations::class.java)
                if (reservation != null) {
                    // Remove the reservation
                    reservationRef.removeValue()
                        .addOnSuccessListener {
                            // Update the reserved status of the item
                            val itemRef = FirebaseManager.database.reference.child("users")
                                .child(reservation.offeringUserId).child("fridge")
                                .child(reservation.itemId)
                            itemRef.updateChildren(mapOf("reserved" to false))
                                .addOnSuccessListener { onSuccess() }
                                .addOnFailureListener { e ->
                                    onFailure(
                                        e.message ?: "Failed to update item status"
                                    )
                                }
                        }
                        .addOnFailureListener { e ->
                            onFailure(
                                e.message ?: "Failed to delete reservation"
                            )
                        }
                } else {
                    onFailure("Reservation not found")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                onFailure(databaseError.message)
            }
        })
    }
}


