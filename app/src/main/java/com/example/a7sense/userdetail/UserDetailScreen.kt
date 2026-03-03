package com.example.a7sense.userdetail

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.runtime.Composable
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
@Composable
fun UserDetailScreen(
    navController: NavController,

){

    val context = LocalContext.current

    val viewModel: UserViewModel = viewModel()

    var name by rememberSaveable { mutableStateOf("") }
    var age by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }

    val message = viewModel.message.value
    val isSaved = viewModel.isSaved.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Age") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Phone Number") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            viewModel.saveUserData(name, age, phone)
        }) {
            Text("Save Details")
        }

        if (message.isNotEmpty()) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        LaunchedEffect(isSaved) {
            if (isSaved) {
                navController.navigate("main") {
                    popUpTo("login") { inclusive = true }
                }
            }
        }

        if (isSaved) {
            // Later navigate to Home screen
        }
    }
}

@Preview
@Composable
fun PreviewUserDetailScreen() {
    UserDetailScreen(navController = rememberNavController())
}