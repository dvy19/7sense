package com.example.a7sense.bmi

data class BmiReq(
    var bmi: Double
)

data class BmiRes(

    val bmi_category: String,
    val input_bmi: Double,
    val top_diseases: List<String>

)