package com.example.a7sense.appScreens.predictDisease

import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PredictionViewModel : ViewModel() {

    private val repository=PredictionRepo()

    var symptomList by mutableStateOf<List<String>>(emptyList())
    val selectedSymptoms = mutableStateListOf<String>()
    var predictionResult by mutableStateOf<String?>(null)
    var isLoading by mutableStateOf(false)
    var isFetchingSymptoms by mutableStateOf(false)

    init {
        loadSymptoms()
    }

    fun loadSymptoms() {
        viewModelScope.launch {
            isFetchingSymptoms = true
            try {
                val data = repository.getSymptomsFromFirestore()
                symptomList = data
            } finally {
                isFetchingSymptoms = false
            }
        }
    }



    fun toggleSymptom(symptom: String) {
        if (selectedSymptoms.contains(symptom)) {
            selectedSymptoms.remove(symptom)
        } else {
            selectedSymptoms.add(symptom)
        }
    }

    fun getPrediction(age: Int, gender: String, bmi: Double) {
        viewModelScope.launch {
            isLoading = true
            val request = PredictionReq(
                symptoms = selectedSymptoms.toList(),
                age = age,
                gender = gender,
                smoking = "never", // Defaulting for now
                alcohol = "occasional",
                bmi = bmi
            )
            val response = repository.predictDisease(request)
            predictionResult = response?.disease_name ?: "Unknown"
            predictionResult = response?.disease_category ?: "Unknown"

            isLoading = false
        }
    }
}