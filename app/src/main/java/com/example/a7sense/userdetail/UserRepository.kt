package com.example.a7sense.userdetail

import com.example.a7sense.auth.User
import com.google.firebase.firestore.FirebaseFirestore

class UserRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private var isSaved=false


    fun saveUser(user: User, callback: (Boolean, String) -> Unit) {

        firestore.collection("users")
            .document(user.uid)
            .set(user)
            .addOnSuccessListener {
                callback(true, "User Data Saved")
                isSaved=true
            }
            .addOnFailureListener { e ->
                callback(false, e.message ?: "Error")
            }
    }


}