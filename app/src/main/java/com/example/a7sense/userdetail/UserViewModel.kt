package com.example.a7sense.userdetail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.a7sense.auth.AuthRepository
import com.example.a7sense.auth.User

class UserViewModel : ViewModel(){
    private val userRepository = UserRepository()
    private val authRepository = AuthRepository()

    var message = mutableStateOf("")
    var isSaved = mutableStateOf(false)

    fun saveUserData(
        name: String,
        age: String,
        phone: String,
        bloodGroup: String,
        alcoholUse: String,
        smokingStatus: String,
        state: String,
        city: String,
        dietStatus: String
    ) {

        val uid = authRepository.getCurrentUser()

        if (uid != null) {

            val user = User(
                uid = uid,
                name = name,
                age = age,
                phone = phone,
                bmi = 0.0f
            )

            userRepository.saveUser(user) { success, msg ->
                isSaved.value = success
                message.value = msg
            }

        } else {
            message.value = "User not logged in"
        }
    }
}