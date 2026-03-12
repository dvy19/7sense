package com.example.a7sense.medicine

import com.google.firebase.firestore.FirebaseFirestore
class MedicineRepository {

    private val db = FirebaseFirestore.getInstance()

    fun searchMedicines(
        query: String,
        onResult: (List<medicine_name>) -> Unit
    ) {

        val searchQuery = query.lowercase()

        db.collection("medicine")
            .whereGreaterThanOrEqualTo("search_name", searchQuery)
            .whereLessThanOrEqualTo("search_name", searchQuery + "\uf8ff")
            .get()
            .addOnSuccessListener { result ->

                val medicines = mutableListOf<medicine_name>()

                for (doc in result) {
                    val medicine = doc.toObject(medicine_name::class.java)
                    medicines.add(medicine)
                }

                val uniqueList = medicines.distinctBy { it.name }

                onResult(uniqueList)
            }
    }
}