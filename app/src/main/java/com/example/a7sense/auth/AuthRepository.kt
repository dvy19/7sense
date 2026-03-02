package com.example.a7sense.auth

import com.google.firebase.auth.FirebaseAuth

class AuthRepository {

    private val auth= FirebaseAuth.getInstance();


    fun signup(email:String, password:String, callback:(Boolean,String)->Unit){

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task->
                if(task.isSuccessful){
                    callback(true,"User Created Successfully")
                }
                else{
                    callback(false,task.exception?.message.toString())
                }
            }
    }

    fun login(email:String, password:String, callback:(Boolean,String)->Unit){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task->
                if(task.isSuccessful) {
                    callback(true, "User Logged In Successfully")
                }
                else{
                    callback(false,task.exception?.message.toString())
                }
            }
    }


    fun getCurrentUser()=auth.currentUser;

    }






