package com.example.a7sense.bmi

// Retrofit interface to define the POST request
import retrofit2.http.Body
import retrofit2.http.POST

interface BmiDiseaseApiService {
    @POST("getDiseaseViaBmi")
    suspend fun getDiseases(@Body request: BmiReq): BmiRes
}