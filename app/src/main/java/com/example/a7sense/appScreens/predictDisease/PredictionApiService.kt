package com.example.a7sense.appScreens.predictDisease

import retrofit2.http.Body
import retrofit2.http.POST

interface PredictionApiService {
    @POST("symptomsPrediction")
    suspend fun getDiseasePrediction(
        @Body request: PredictionReq
    ): PredictionRes
}