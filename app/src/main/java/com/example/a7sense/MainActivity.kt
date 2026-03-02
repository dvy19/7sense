package com.example.a7sense

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
    object Login:Screen("login")
    object Signup:Screen("signup")
    object UserDetail:Screen("userDetail")

}


