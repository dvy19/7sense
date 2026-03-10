package com.example.a7sense.appScreens.DashBoard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.a7sense.auth.User
import com.example.a7sense.userdetail.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


data class DashboardCard(
    val heading: String,
    val subheading: String,
    val buttonText: String,
    val onClick:()->Unit
)




@Composable
fun DashboardScreen(mainNavController: NavController){

    val blueGradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF4facfe), Color(0xFF00f2fe))
    )

    val db= FirebaseFirestore.getInstance();
    val auth= FirebaseAuth.getInstance();

    val currentUser=auth.currentUser?.uid;



    val viewModel: DashboardViewModel=viewModel()


    val user = viewModel.user
    val isLoading = viewModel.isLoading

    var showDialog by remember { mutableStateOf(false) }
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var bmi by remember { mutableStateOf<Float?>(null) }
    LaunchedEffect(currentUser) {
        currentUser?.let {
            viewModel.loadUser(it)
        }
    }


    val cards = listOf(
        DashboardCard(
            heading = "TOP 5 Diseases Age Group wise",
            subheading = "Check disease prevalent in your age group",
            buttonText = "View",
            onClick = { mainNavController.navigate("cityVsname") }
        ),
        DashboardCard(
            heading = "Top Disease in your State",
            subheading = "View Demographic analysis",
            buttonText = "Continue",
            onClick={},
        ),
        
        DashboardCard(
            heading = "Explore More in the App",
            subheading = "View Health Related Analysis including medicines, diet and more",
            buttonText = "Explore",
            onClick = {  }
        )

    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        //verticalArrangement = Arrangement.spacedBy(24.dp) // spacing between rows
    ){


        HealthLocationCard(stateName = user?.state ?: "Uttar Pradesh", cityName = user?.city ?: "Kanpur")

        Spacer(modifier=Modifier.height(12.dp))

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
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(blueGradient)
                    .padding(20.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (user?.bmi == null) {
                        // Empty State UI
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = Color.White.copy(alpha = 0.8f),
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Track Your BMI",
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Monitor your health progress easily",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { showDialog=true },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Calculate Now", color = Color(0xFF007EF4))
                        }
                    } else {
                        // Active State UI
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Your BMI",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color.White.copy(alpha = 0.8f)
                                )
                                Text(
                                    text = String.format("%.1f", user.bmi),
                                    style = MaterialTheme.typography.displayMedium,
                                    color = Color.White,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            }

                            // Modern Circular visualizer
                            Box(contentAlignment = Alignment.Center) {
                                CircularProgressIndicator(
                                    progress = (user.bmi.toFloat() / 40f).coerceIn(0f, 1f),
                                    modifier = Modifier.size(80.dp),
                                    color = Color.White,
                                    strokeWidth = 8.dp,
                                    trackColor = Color.White.copy(alpha = 0.2f)
                                )
                                IconButton(onClick = {showDialog=true}) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Edit",
                                        tint = Color.White
                                    )
                                }
                            }
                        }

                        // Subtle Badge for Status
                        Surface(
                            color = Color.White.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(50),
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(top = 12.dp)
                        ) {
                            Text(
                                text = getBmiCategory(user.bmi),
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }

    // Helper for dynamic labels


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
fun getBmiCategory(bmi: Float): String {
    return when {
        bmi < 18.5 -> "Underweight"
        bmi < 25 -> "Healthy Weight"
        bmi < 30 -> "Overweight"
        else -> "Obese"
    }
}


@Composable
fun DashboardCardItem(
    card: DashboardCard
) {
    val softBlue = Color(0xFFE3F2FD) // Light background
    val healthBlue = Color(0xFF2196F3) // Primary blue
    val deepNavy = Color(0xFF1A237E) // Contrast text color
    Card(
        modifier = Modifier
            .width(250.dp)
            .height(180.dp),
        colors = CardDefaults.cardColors(containerColor = softBlue),
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
                Text(card.buttonText
                )
            }
        }
    }
}

@Composable
fun HealthLocationCard(stateName: String, cityName: String) {
    // Custom Health Blue Palette
    val softBlue = Color(0xFFE3F2FD) // Light background
    val healthBlue = Color(0xFF2196F3) // Primary blue
    val deepNavy = Color(0xFF1A237E) // Contrast text color

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = softBlue),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Text Section
            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    text = stateName,
                    style = MaterialTheme.typography.titleMedium,
                    color = deepNavy,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = cityName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = healthBlue.copy(alpha = 0.8f),
                    fontWeight = FontWeight.Medium
                )
            }

            // Icon Section with a soft circular background
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = softBlue,
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location Icon",
                    tint = healthBlue,

                    modifier = Modifier
                        .size(24.dp)
                        .scale(2.1f)
                )
            }
        }
    }
}



@Preview
@Composable
fun PreviewDashboardScreen() {
    DashboardScreen(mainNavController = rememberNavController())
}

