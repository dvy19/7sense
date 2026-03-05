package com.example.a7sense.appScreens.profile

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.a7sense.auth.User

class ProfileViewModel : ViewModel() {

    private val repository = ProfileRepo()

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