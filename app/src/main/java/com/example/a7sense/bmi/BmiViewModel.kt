package com.example.a7sense.bmi

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BmiViewModel : ViewModel() {
    // UI State: Holds the response data
    var responseState = mutableStateOf<BmiRes?>(null)
    var isLoading = mutableStateOf(false)

    // Setup Retrofit (Ideally done in a separate file, but kept here for simplicity)
    private val api = Retrofit.Builder()
        .baseUrl("https://sevensenseapis.onrender.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(BmiDiseaseApiService::class.java)

    fun fetchDiseasePrediction(userBmi: Double) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                val result = api.getDiseases(BmiReq(userBmi))
                responseState.value = result
            } catch (e: Exception) {
                // Handle error (e.g., no internet or server down)
                println("Error: ${e.message}")
            } finally {
                isLoading.value = false
            }
        }
    }
}