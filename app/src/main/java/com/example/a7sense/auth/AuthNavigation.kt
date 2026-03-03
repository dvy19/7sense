package com.example.a7sense.auth

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.a7sense.Screen
import com.example.a7sense.appScreens.MainScreen
import com.example.a7sense.userdetail.UserDetailScreen


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

        composable(Screen.UserDetail.route){
            UserDetailScreen(navController)
        }

        composable(Screen.Main.route) {
            MainScreen(navController)   // 👈 Bottom Navigation starts here
        }







    }

}