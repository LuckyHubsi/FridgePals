package com.example.fridgepals.repository

import com.example.fridgepals.data.FirebaseManager
import com.example.fridgepals.data.model.User
import com.google.firebase.auth.FirebaseAuth

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

    fun logoutUser() {
        auth.signOut()
    }
}