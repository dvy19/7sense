package com.example.a7sense.appScreens.predictDisease

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class PredictionRepo {
    private val db = FirebaseFirestore.getInstance()
    private val apiService: PredictionApiService = RetrofitClient.instance

    // Fetch symptoms from Firestore: symptoms -> symptoms_data -> 44 fields
    suspend fun getSymptomsFromFirestore(): List<String> {
        return try {
            val document = db.collection("symptoms").document("symptoms_data").get().await()
            // Fetch the array named "symptoms"
            val symptomsArray = document.get("symptoms") as? List<*>

            // Convert to List<String> and filter out any non-string values just in case
            symptomsArray?.mapNotNull { it.toString() } ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun predictDisease(request: PredictionReq): PredictionRes? {
        return try {
            apiService.getDiseasePrediction(request)
        } catch (e: Exception) {
            null
        }
    }
}