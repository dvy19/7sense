package com.example.a7sense

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.a7sense.auth.AuthNavigation
import com.example.a7sense.ui.theme._7SenseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _7SenseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    AuthNavigation()

                }
            }
        }
    }
}

sealed class Screen(val route:String){

    object Splash:Screen("splash")

    object Login:Screen("login")

    object Signup:Screen("signup")

    object MultiStepForm:Screen("userForm")


    object Main : Screen("main")

    object Dashboard : Screen("dashboard")
    object Health : Screen("health")
    object Profile : Screen("profile")

    object DiseasePredict:Screen("diseasePredict")
    object Bmi: Screen("BmiFeature")

}

data class BottomNavItem(
    val label: String,
    val route: String,
    val icon: ImageVector
)
val bottomItems = listOf(
    BottomNavItem("Dashboard", Screen.Dashboard.route, Icons.Default.Home),
    BottomNavItem("Your Health", Screen.Health.route, Icons.Default.Favorite),
    BottomNavItem("Profile", Screen.Profile.route, Icons.Default.Person)
)

@Composable
fun BottomNavigationBar(navController: NavController) {

    val currentRoute =
        navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar {

        bottomItems.forEach { item ->

            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(Screen.Dashboard.route)
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(item.icon, contentDescription = item.label)
                },
                label = {
                    Text(text = item.label)
                }
            )
        }
    }
}


