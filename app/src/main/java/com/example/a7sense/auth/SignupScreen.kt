package com.example.a7sense.auth

import androidx.compose.runtime.Composable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.a7sense.auth.AuthViewModel
import com.example.a7sense.ui.theme._7SenseTheme




@Composable
fun SignupScreen( navController: NavController,
                 viewModel: AuthViewModel = viewModel()
){

    val context=LocalContext.current;

    val message=viewModel.authMessage.value
    val isSuccess=viewModel.isSuccess.value



    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Basic layout with padding and spacing
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "7Sense Signup",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Email input field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))


        OutlinedTextField(
            value = password,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Lock Icon",
                    tint = Color.Gray
                )
            },
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),

            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Submit button
        Button(
            onClick = {

                viewModel.signup(email.trim(),password)
                      },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit")
        }

        LaunchedEffect(isSuccess) {
            if (isSuccess) {
                navController.navigate("userDetail") {
                    popUpTo("signup") { inclusive = true }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Create account button
        TextButton(
            onClick = { navController.navigate("login") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login Here")
        }
    }
}



@Preview
@Composable
fun PreviewSignup(){
    SignupScreen(navController = rememberNavController());
}