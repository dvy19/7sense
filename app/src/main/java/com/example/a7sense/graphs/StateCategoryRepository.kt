package com.example.a7sense.graphs

import com.google.firebase.firestore.FirebaseFirestore

class StateRepository {

    private val db = FirebaseFirestore.getInstance()

    // Get list of states
    fun getStates(onResult: (List<String>) -> Unit) {
        db.collection("stateVsCategory")
            .get()
            .addOnSuccessListener { snapshot ->
                val states = snapshot.documents.map { it.id }
                onResult(states)
            }
    }


    fun getStateData(
        state: String,
        onResult: (Map<String, Long>) -> Unit
    ) {
        db.collection("stateVsCategory")
            .document(state)
            .get()
            .addOnSuccessListener { document ->
                val data = document.data as? Map<String, Long>
                if (data != null) {
                    onResult(data)
                }
            }
    }
}