package com.example.a7sense.appScreens.predictDisease

// The request body for your API
data class PredictionReq(
    val symptoms: List<String>,
    val age: Int,
    val gender: String,
    val smoking: String,
    val alcohol: String,
    val bmi: Double
)

// The response from your API (adjust fields based on actual API response)
data class PredictionRes(
    val disease_name: String? = null,
    val disease_category: String? = null
)