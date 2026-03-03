package com.example.a7sense.bmi
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun BmiFeaturesScreen(
    navController: NavController,
    viewModel: BmiViewModel = viewModel()) {
    val result = viewModel.responseState.value
    val loading = viewModel.isLoading.value

    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var bmi by remember { mutableStateOf<Float?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp) // spacing between rows
    ) {
        // First Row: BMI Calculator
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = "BMI Calculator", style = MaterialTheme.typography.titleLarge)

            OutlinedTextField(
                value = height,
                onValueChange = { height = it },
                label = { Text("Height (m)") }
            )

            OutlinedTextField(
                value = weight,
                onValueChange = { weight = it },
                label = { Text("Weight (kg)") }
            )

            Button(onClick = {
                val h = height.toFloatOrNull()
                val w = weight.toFloatOrNull()
                if (h != null && h > 0 && w != null && w > 0) {
                    bmi = (w / (h * h))
                }
            }) {
                Text("Calculate BMI")
            }
        }

        // Second Row: Disease Predictor
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = "BMI Disease Predictor", style = MaterialTheme.typography.headlineMedium)

            Button(
                onClick = {
                    if (bmi != null) {
                        viewModel.fetchDiseasePrediction(bmi!!.toDouble())
                    }
                },
                enabled = !loading
            ) {
                if (loading) Text("Loading...") else Text("Analyze BMI: $bmi")
            }

            result?.let {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Category: ${it.bmi_category}", style = MaterialTheme.typography.titleLarge)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Top Prevalent Diseases:", style = MaterialTheme.typography.labelLarge)

                        if (it.top_diseases.isEmpty()) {
                            Text("No diseases found for this BMI range in the dataset.", color = Color.Red)
                        } else {
                            it.top_diseases.forEach { disease ->
                                Text(text = "• $disease")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewBmiFeaturesScreen() {
    BmiFeaturesScreen(navController = rememberNavController(

    ))
}