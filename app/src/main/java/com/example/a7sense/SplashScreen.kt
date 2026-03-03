package com.example.a7sense


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

@Composable
fun SplashScreen(
    onUserLoggedIn: () -> Unit,
    onUserLoggedOut: () -> Unit
) {
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    LaunchedEffect(Unit) {
        val currentUser = auth.currentUser

        if (currentUser == null) {
            onUserLoggedOut()
        } else {
            try {
                val snapshot = db.collection("users")
                    .document(currentUser.uid)
                    .get()
                    .await()

                if (snapshot.exists()) {
                    onUserLoggedIn()
                } else {
                    onUserLoggedOut()
                }
            } catch (e: Exception) {
                onUserLoggedOut()
            }
        }
    }

    // UI (optional loader)
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
