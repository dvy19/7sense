package com.example.a7sense.medicine

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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



@Composable
fun MedicinePreviewScreen() {
    val scrollState = rememberScrollState()

    Scaffold(
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
        ) {
            // 1. Full Width Top Image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .background(SkyBlueSecondary)
            ) {
                Image(
                    painter = painterResource(id = android.R.drawable.ic_menu_gallery), // Replace with your image
                    contentDescription = "Medicine Header",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            // Textual Content Container
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
            ) {
                // 2. Subheading & Heading
                Text(
                    text = "MEDICINE",
                    style = MaterialTheme.typography.labelLarge,
                    color = SkyBluePrimary,
                    letterSpacing = 2.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Amoxicillin 500mg Capsule",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Black,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(20.dp))

                // 3. Manufacturer Card (Full Width)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = SkyBlueSecondary.copy(alpha = 0.4f)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Business, contentDescription = null, tint = SkyBluePrimary)
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text("MANUFACTURER", style = MaterialTheme.typography.labelSmall, color = TextGrey)
                            Text("GlaxoSmithKline (GSK) Pharma", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 4. Content Sections
                DetailSection(title = "Composition", content = "Each capsule contains Amoxicillin Trihydrate equivalent to 500mg Amoxicillin. Contains magnesium stearate and gelatin shell.")
                DetailSection(title = "Uses", content = "Used to treat various bacterial infections including pneumonia, tonsillitis, and urinary tract infections.")
                DetailSection(title = "Side Effects", content = "Common side effects include nausea, rash, or dizziness. Consult your doctor if symptoms persist.")

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))
                Spacer(modifier = Modifier.height(24.dp))

                // 5. Review Section with Progress Bars
                Text("Patient Reviews", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))

                ReviewBar(label = "Excellent", progress = 0.8f, color = Color(0xFF4CAF50))
                ReviewBar(label = "Average", progress = 0.4f, color = Color(0xFFFFC107))
                ReviewBar(label = "Poor", progress = 0.15f, color = Color(0xFFF44336))

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Composable
fun DetailSection(title: String, content: String) {
    Column(modifier = Modifier.padding(vertical = 12.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = content,
            style = MaterialTheme.typography.bodyMedium,
            color = TextGrey,
            lineHeight = 20.sp
        )
    }
}

@Composable
fun ReviewBar(label: String, progress: Float, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, modifier = Modifier.width(80.dp), style = MaterialTheme.typography.bodySmall)
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .weight(1f)
                .height(8.dp)
                .clip(CircleShape),
            color = color,
            trackColor = SkyBlueSecondary,
        )
        Text(
            "${(progress * 100).toInt()}%",
            modifier = Modifier.width(40.dp).padding(start = 8.dp),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
fun PreviewMedicinePreviewScreen() {
    MedicinePreviewScreen()
}