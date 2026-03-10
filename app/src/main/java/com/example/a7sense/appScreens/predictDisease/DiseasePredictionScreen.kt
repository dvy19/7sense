package com.example.a7sense.appScreens.predictDisease

import androidx.compose.runtime.Composable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.ui.text.style.TextAlign

@Composable
fun DiseasePredictionScreen(
    mainNavController: NavController,
    viewModel: PredictionViewModel=viewModel()) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Select Symptoms")


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Select Symptoms")

            IconButton(onClick = { viewModel.loadSymptoms() }) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Refresh Symptoms"
                )
            }
        }


        // Scrollable list of symptoms
        Box(modifier = Modifier.weight(1f)) {
            if (viewModel.isFetchingSymptoms) {
                // Show a loading spinner in the middle
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (viewModel.symptomList.isEmpty()) {
                // Show this if no data was found
                Text(
                    "No symptoms found. Please check your internet and refresh.",
                    modifier = Modifier.align(Alignment.Center).padding(16.dp),
                    textAlign = TextAlign.Center
                )
            } else {
                // The actual list
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(viewModel.symptomList) { symptom ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { viewModel.toggleSymptom(symptom) }
                        ) {
                            Checkbox(
                                checked = viewModel.selectedSymptoms.contains(symptom),
                                onCheckedChange = { viewModel.toggleSymptom(symptom) }
                            )
                            Text(text = symptom, modifier = Modifier.padding(start = 8.dp))
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.getPrediction(32, "male", 24.5) },
            modifier = Modifier.fillMaxWidth(),
            enabled = !viewModel.isLoading
        ) {
            Text(if (viewModel.isLoading) "Predicting..." else "Get Prediction")
        }

        viewModel.predictionResult?.let {
            Text(
                "Result: $it",
                modifier = Modifier.padding(top = 16.dp),
                color = Color.Blue,

            )
        }
    }
}

@Preview
@Composable
fun PreviewPredictionScreen(){
    DiseasePredictionScreen(mainNavController = rememberNavController())
}