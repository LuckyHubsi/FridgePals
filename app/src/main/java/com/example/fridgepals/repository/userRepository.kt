package com.example.fridgepals.repository

import com.example.fridgepals.data.FirebaseManager
import com.example.fridgepals.data.model.User
import java.security.MessageDigest


class UserRepository {
    fun registerUser(user: User) {
        FirebaseManager.database.reference.child("users").push().setValue(user)
    }

    /* other Version that can be implemented when we have a look on error handling
    fun registerUser(user: User, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        FirebaseManager.database.reference.child("users").push().setValue(user)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }

    kann im UI Viewmodel geaddet werden
    userRepository.handleRegistration(user,
    onSuccess = {
        // Handle success, e.g., Log message or UI update
    },
    onFailure = { e ->
        // Handle failure, e.g., Log error message or show error in UI
        Log.e("RegistrationError", "Failed to register user: ${e.message}")
    }
)
     */

    fun hashPassword(password: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

}