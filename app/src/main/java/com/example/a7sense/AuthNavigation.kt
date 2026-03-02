package com.example.a7sense

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.a7sense.auth.LoginScreen
import com.example.a7sense.auth.SignupScreen


@Composable
fun AuthNavigation(){

    val navController= rememberNavController();

    NavHost(
        navController = navController,
        startDestination = Screen.Signup.route
    ){

        composable(Screen.Signup.route){
            SignupScreen(navController)
        }

        composable(Screen.Login.route){
            LoginScreen(navController)
        }

        composable(Screen.userDetail.route){
            UserDetailScreen()
        }








    }

}