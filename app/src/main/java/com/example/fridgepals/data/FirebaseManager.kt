package com.example.fridgepals.data

import com.google.firebase.database.FirebaseDatabase

object FirebaseManager {
    val database: FirebaseDatabase by lazy {
        FirebaseDatabase.getInstance("https://ccl3-fridgepals-default-rtdb.europe-west1.firebasedatabase.app")
    }
}

