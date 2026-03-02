package com.example.a7sense.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class AuthViewModel:ViewModel() {

    private val authRepository=AuthRepository()

    var authMessage= mutableStateOf("");
    var isSuccess=mutableStateOf(false);


    fun signup(email:String, password:String){
        authRepository.signup(email, password){ success,message->
            authMessage.value=message;
            isSuccess.value=success;
        }

    }

    fun login(email: String,password: String){
        authRepository.login(email, password){ success,message->
            authMessage.value=message;
            isSuccess.value=success;

        }

    }


}