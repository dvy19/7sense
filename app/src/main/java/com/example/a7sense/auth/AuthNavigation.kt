package com.example.a7sense.auth

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.a7sense.Screen
import com.example.a7sense.SplashScreen
import com.example.a7sense.appScreens.MainScreen
import com.example.a7sense.userdetail.MultiStepForm


@Composable
fun AuthNavigation(){

    val rootNavController= rememberNavController();

    NavHost(
        navController = rootNavController,
        startDestination = Screen.Splash.route
    ){

        composable("splash") {
            SplashScreen(
                onUserLoggedIn = {
                    rootNavController.navigate("main") {
                        popUpTo("splash") { inclusive = true }
                    }
                },
                onUserLoggedOut = {
                    rootNavController.navigate("signup") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Signup.route){
            SignupScreen(rootNavController)
        }

        composable(Screen.Login.route){
            LoginScreen(rootNavController)
        }

        composable(Screen.MultiStepForm.route){
            MultiStepForm(rootNavController)
        }

        composable(Screen.Main.route) {
            MainScreen(rootNavController)   // 👈 Bottom Navigation starts here
        }







    }

}