package com.example.a7sense.appScreens.DashBoard

import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    private val repository = DashboardRepo()

    fun saveBMI(uid: String, bmi: Float) {
        repository.updateBMI(uid, bmi)
    }
}