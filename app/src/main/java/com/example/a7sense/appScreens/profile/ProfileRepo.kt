package com.example.a7sense.appScreens.profile

import com.example.a7sense.auth.User
import com.google.firebase.firestore.FirebaseFirestore

class ProfileRepo {

    private val db = FirebaseFirestore.getInstance()

    fun getUser(userId: String, onResult: (User?) -> Unit) {

        db.collection("users")
            .document(userId)
            .addSnapshotListener { value, error ->

                if (error != null) {
                    onResult(null)
                    return@addSnapshotListener
                }

                if (value != null && value.exists()) {
                    onResult(value.toObject(User::class.java))
                }
            }
    }
}