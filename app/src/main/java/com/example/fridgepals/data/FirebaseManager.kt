package com.example.fridgepals.data

import com.example.fridgepals.data.model.User
import com.google.firebase.database.FirebaseDatabase
import java.security.MessageDigest

object FirebaseManager {
    val database: FirebaseDatabase by lazy {
        FirebaseDatabase.getInstance("https://ccl3-fridgepals-default-rtdb.europe-west1.firebasedatabase.app")
    }
}

