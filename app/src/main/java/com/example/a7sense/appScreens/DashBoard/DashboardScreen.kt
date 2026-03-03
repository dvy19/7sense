package com.example.a7sense.appScreens.DashBoard

import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a7sense.auth.User
import com.example.a7sense.userdetail.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


data class DashboardCard(
    val heading: String,
    val subheading: String,
    val buttonText: String
)




@Composable
fun DashboardScreen(){


    val db= FirebaseFirestore.getInstance();
    val auth= FirebaseAuth.getInstance();

    val currentUser=auth.currentUser?.uid;

    var user by remember { mutableStateOf<User?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    val viewModel: DashboardViewModel=viewModel()
    LaunchedEffect(currentUser) {

        if (currentUser == null) return@LaunchedEffect

        db.collection("users")
            .document(currentUser)
            .addSnapshotListener { value, error ->

                if (error != null) return@addSnapshotListener

                if (value != null && value.exists()) {
                    user = value.toObject(User::class.java)
                }

                isLoading = false
            }
    }


    var showDialog by remember { mutableStateOf(false) }
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var bmi by remember { mutableStateOf<Float?>(null) }


    val cards = listOf(
        DashboardCard(
            heading = "TOP 5 Diseases Age Group wise",
            subheading = "Check disease prevalent in your age group",
            buttonText = "View"
        ),
        DashboardCard(
            heading = "Daily Health Tips",
            subheading = "Quick tips for a healthier lifestyle",
            buttonText = "Explore"
        ),
        DashboardCard(
            heading = "Track Your Fitness",
            subheading = "Monitor steps, calories, and workouts",
            buttonText = "Open"
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        //verticalArrangement = Arrangement.spacedBy(24.dp) // spacing between rows
    ){
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(cards) { card ->
                DashboardCardItem(card)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "BMI Feature", style = MaterialTheme.typography.titleLarge)
                if (user?.bmi == null) {

                    Text(
                        text = "Calculate your BMI",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Button(onClick = { showDialog = true }) {
                        Text("Add")
                    }

                } else {

                    Text(
                        text = "Your Current BMI: ${user?.bmi}",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Button(onClick = { showDialog = true }) {
                        Text("Edit")
                    }
                }
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Calculate BMI") },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = height,
                            onValueChange = { height = it },
                            label = { Text("Height (cm)") }
                        )
                        OutlinedTextField(
                            value = weight,
                            onValueChange = { weight = it },
                            label = { Text("Weight (kg)") }
                        )
                    }
                },

                confirmButton = {
                    Button(onClick = {
                        val h = height.toFloatOrNull()
                        val w = weight.toFloatOrNull()
                        if (h != null && h > 0 && w != null && w > 0) {
                            // Convert height from cm to meters
                            val heightInMeters = h / 100
                            bmi = w / (heightInMeters * heightInMeters)
                            showDialog = false // close popup after calculation
                        }
                    }) {
                        Text("Calculate BMI")
                    }
                },
                dismissButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }


        // Show BMI result below card
        bmi?.let {

            viewModel.saveBMI(currentUser.toString(),bmi!!);


        }
    }
}



@Composable
fun DashboardCardItem(card: DashboardCard) {
    Card(
        modifier = Modifier
            .width(250.dp)
            .height(180.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = card.heading,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = card.subheading,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Button(
                onClick = { /* TODO: Handle button click */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(card.buttonText)
            }
        }
    }
}

@Preview
@Composable
fun PreviewDashboardScreen() {
    DashboardScreen()
}

