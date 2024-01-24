package com.example.fridgepals.repository

import android.util.Log
import androidx.navigation.compose.rememberNavController
import com.example.fridgepals.data.FirebaseManager
import com.example.fridgepals.data.model.User
import com.example.fridgepals.ui.view.Login
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

object UserRepository {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    fun registerUser(email: String, password: String, user: User, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Get the unique user ID from Firebase Authentication
                    val firebaseUser = auth.currentUser
                    val userId = firebaseUser?.uid

                    // Store additional user data in Firebase Realtime Database
                    if (userId != null) {
                        FirebaseManager.database.reference.child("users").child(userId).setValue(user)
                            .addOnSuccessListener {
                                onSuccess()
                            }
                            .addOnFailureListener { e ->
                                onFailure(e.message ?: "Failed to save user data")
                            }
                    } else {
                        onFailure("Failed to get user ID")
                    }
                } else {
                    // Handle failed user creation
                    onFailure(task.exception?.message ?: "Failed to create user")
                }
            }
    }

    fun loginUser(email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Login success
                    onSuccess()
                } else {
                    // Login failure
                    onFailure(task.exception?.message ?: "Authentication failed.")
                }
            }
    }

    fun getUsername(onResult: (String) -> Unit) {
        val currentUser = auth.currentUser
        val userId = currentUser?.uid ?: return onResult("Unknown")

        val userRef = FirebaseManager.database.reference.child("users").child(userId)
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                onResult(user?.name ?: "Unknown")
            }

            override fun onCancelled(error: DatabaseError) {
                onResult("Error fetching name")
            }
        })
    }

    fun logoutUser() {
        auth.signOut()
    }
}