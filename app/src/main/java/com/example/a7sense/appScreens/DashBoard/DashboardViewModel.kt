package com.example.a7sense.appScreens.DashBoard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.a7sense.auth.User

class DashboardViewModel : ViewModel() {

    private val repository = DashboardRepo()

    fun saveBMI(uid: String, bmi: Float) {
        repository.updateBMI(uid, bmi)
    }

    var user by mutableStateOf<User?>(null)
        private set

    var isLoading by mutableStateOf(true)
        private set

    fun loadUser(userId: String) {

        repository.getUser(userId) { result ->
            user = result
            isLoading = false
        }
    }
}