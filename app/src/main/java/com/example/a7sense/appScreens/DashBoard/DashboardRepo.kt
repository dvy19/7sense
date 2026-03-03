package com.example.a7sense.appScreens.DashBoard

import com.google.firebase.firestore.FirebaseFirestore

class DashboardRepo{

        private val db = FirebaseFirestore.getInstance()

        fun updateBMI(uid: String, bmi: Float) {
            db.collection("users")
                .document(uid)
                .update("bmi", bmi)
        }
    }
