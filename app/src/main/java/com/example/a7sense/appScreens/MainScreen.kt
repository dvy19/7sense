package com.example.a7sense.appScreens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.a7sense.BottomNavigationBar
import com.example.a7sense.Screen
import com.example.a7sense.appScreens.DashBoard.DashboardScreen
import com.example.a7sense.bmi.BmiFeaturesScreen

@Composable
fun MainScreen(navController: NavController) {

    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = Screen.Dashboard.route,
            modifier = Modifier.padding(paddingValues)
        ) {

            composable(Screen.Dashboard.route) {
                DashboardScreen()
            }

            composable(Screen.Health.route) {
                HealthScreen()
            }

            composable(Screen.Profile.route) {
                ProfileScreen(navController)
            }

            composable(Screen.Bmi.route) {

                BmiFeaturesScreen(navController)

            }
        }
    }
}