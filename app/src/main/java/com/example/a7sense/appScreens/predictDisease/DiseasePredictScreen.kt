package com.example.a7sense.appScreens.predictDisease

import androidx.compose.runtime.Composable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.a7sense.auth.AuthViewModel
import com.example.a7sense.auth.User
import com.example.a7sense.ui.theme._7SenseTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.foundation.lazy.grid.items

@Composable
fun DiseasePredictionScreen(
    navController: NavController,
    viewModel: PredictDiseaseViewModel=viewModel()
) {

    val symptoms = viewModel.symptomsList
    val selected = viewModel.selectedSymptoms
    val result = viewModel.predictionResult
    val isLoading = viewModel.isLoading


    var auth=FirebaseAuth.getInstance()
    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

    val db= FirebaseFirestore.getInstance()
    var user by remember { mutableStateOf<User?>(null) }
    var isUserLoading by remember { mutableStateOf(true) }



    LaunchedEffect(currentUserId) {

        if (currentUserId == null) return@LaunchedEffect

        db.collection("users")
            .document(currentUserId)
            .addSnapshotListener { value, error ->

                if (error != null) return@addSnapshotListener

                if (value != null && value.exists()) {
                    user = value.toObject(User::class.java)
                }

                isUserLoading = false
            }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Select Symptoms",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.weight(1f)
        ) {
            items(symptoms) { symptom ->

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = selected.contains(symptom),
                        onCheckedChange = {
                            viewModel.onSymptomChecked(symptom, it)
                        }
                    )

                    Text(text = symptom)
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                viewModel.predict(
                    age = user!!.age.toInt(),
                    gender = user!!.gender,
                    smoking = user!!.smokingStatus,
                    alcohol = user!!.alcoholUse,
                    bmi = user!!.bmi.toDouble()
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Predict Disease")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator()
        }

        result?.let {
            Text(
                text = "Disease: ${it.predicted_disease}",
                fontSize = 18.sp
            )

            Text(
                text = "Category: ${it.predicted_category}",
                fontSize = 16.sp
            )
        }
    }
}