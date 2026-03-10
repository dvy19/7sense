package com.example.a7sense.appScreens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.a7sense.BottomNavigationBar
import com.example.a7sense.Screen
import com.example.a7sense.appScreens.DashBoard.DashboardScreen
import com.example.a7sense.appScreens.predictDisease.DiseasePredictionScreen
import com.example.a7sense.appScreens.profile.ProfileScreen
import com.example.a7sense.bmi.BmiFeaturesScreen
import com.example.a7sense.graphs.city.CityNameScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MainScreen(rootNavController: NavController) {

    val mainNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(mainNavController)
        }
    ) { paddingValues ->

        NavHost(
            navController = mainNavController,
            startDestination = Screen.Dashboard.route,
            modifier = Modifier.padding(paddingValues)
        ) {

            composable(Screen.Dashboard.route) {
                DashboardScreen(mainNavController)
            }

            composable(Screen.Health.route) {
                HealthScreen(mainNavController)
            }

            composable(Screen.Profile.route) {
                ProfileScreen(

                    onLogout = {
                        FirebaseAuth.getInstance().signOut()
                        rootNavController.navigate("login") {
                            popUpTo("main") { inclusive = true }
                        }
                    },

                    onBMI = {
                        FirebaseAuth.getInstance().signOut()
                        mainNavController.navigate("BmiFeature") {
                            popUpTo("main") { inclusive = true }
                        }
                    },

                    )
            }

            composable(Screen.Bmi.route) {

                BmiFeaturesScreen(mainNavController)

            }

            composable(Screen.DiseasePredict.route) {

                DiseasePredictionScreen(mainNavController)

            }
            composable(Screen.CityName.route) {

                CityNameScreen(mainNavController)

            }


        }
    }
}

@Preview
@Composable
fun PreviewMainScreen() {
    MainScreen(rootNavController = rememberNavController())
}