package com.example.a7sense.appScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.a7sense.auth.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ProfileScreen(navController: NavController) {

    val db = FirebaseFirestore.getInstance()
    var user by remember { mutableStateOf<User?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    var auth=FirebaseAuth.getInstance()
    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

    LaunchedEffect(currentUserId) {

        if (currentUserId == null) return@LaunchedEffect

        db.collection("users")
            .document(currentUserId)
            .addSnapshotListener { value, error ->

                if (error != null) return@addSnapshotListener

                if (value != null && value.exists()) {
                    user = value.toObject(User::class.java)
                }

                isLoading = false
            }
    }

    // We use Scaffolding to provide a basic material layout structure
    Scaffold(
        topBar = {
            Text(
                "My Profile",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // --- TOP SECTION: Profile Info ---
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Profile Picture (Placeholder)
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray)
                    ) {
                        // Replace with an actual Image later
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            modifier = Alignment.Center.let { Modifier.align(it) }
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    // Name and Age
                    Column {
                        Text(text = "${user?.name}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text(text = "Age: ${user?.age}", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                    }
                }
            }

            // --- BUTTON ROW: Edit & Logout ---
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = { /* TODO */ },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Edit Profile")
                    }

                    Button(
                        onClick = { /* TODO */ },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Icon(Icons.Default.Logout, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Logout")
                    }
                }

                Divider(modifier = Modifier.padding(vertical = 16.dp), thickness = 0.5.dp)
            }

            // --- CARD: Calculate BMI ---
            item {
                HealthActionCard(
                    title = "Calculate BMI",
                    subtitle = "Check your health status and risk factors",
                    icon = Icons.Default.MonitorWeight,
                    onClick = { navController.navigate("bmi") }
                )
            }

            // --- OTHER CARDS: Example (Health History) ---
            item {
                HealthActionCard(
                    title = "Health History",
                    subtitle = "View your previous medical reports",
                    icon = Icons.Default.History,
                    onClick = { /* TODO */ }
                )
            }
        }
    }
}

@Composable
fun HealthActionCard(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(text = subtitle, style = MaterialTheme.typography.bodySmall)
            }

            IconButton(onClick = onClick) {
                Icon(imageVector = Icons.Default.ChevronRight, contentDescription = "Navigate")
            }
        }
    }
}

@Preview
@Composable
fun PreviewProfile(){
    ProfileScreen(navController = rememberNavController())
}