package com.example.a7sense.graphs.city

import com.google.firebase.firestore.FirebaseFirestore

class CityVsNameRepository {

    private val db = FirebaseFirestore.getInstance()

    fun getCityNames(onResult: (List<String>) -> Unit) {

        FirebaseFirestore.getInstance()
            .collection("city")
            .document("analytics")
            .collection("cityVsname")
            .get()
            .addOnSuccessListener { snapshot ->

                val cityNames = snapshot.documents.map { it.id }

                onResult(cityNames)
            }
            .addOnFailureListener {
                onResult(emptyList())
            }
    }

    fun getCityData(
        city: String,
        onResult: (Map<String, Long>) -> Unit
    ) {

        db.collection("city")
            .document("analytics")
            .collection("cityVsname")
            .document(city)
            .get()
            .addOnSuccessListener { document ->

                val data =
                    document.data as? Map<String, Long>
                        ?: emptyMap()

                onResult(data)
            }
    }


}