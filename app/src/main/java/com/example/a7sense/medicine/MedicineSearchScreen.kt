package com.example.a7sense.medicine

import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.runtime.livedata.observeAsState

// Defining the Light Blue Palette
val SkyBluePrimary = Color(0xFF007BFF)
val SkyBlueSecondary = Color(0xFFE3F2FD)
val TextGrey = Color(0xFF757575)


@Composable
fun MedicineSearchScreen(
    viewModel: MedicineViewModel,
    //onMedicineClick: (medicine_name) -> Unit
) {

    var searchText by remember { mutableStateOf("") }

    val medicines by viewModel.medicineList.observeAsState(emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {

            OutlinedTextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                },
                label = { Text("Search Medicine") },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    viewModel.searchMedicines(searchText)
                }
            ) {
                Text("Search")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {

            items(medicines) { medicine ->

                MedicineItem(
                    medicine = medicine,
                    onClick = { }
                )

            }

        }
    }

}


@Composable
fun MedicineItem(
    medicine: medicine_name,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Text(
            text = medicine.name,
            modifier = Modifier.padding(16.dp),
            fontSize = 18.sp
        )

    }
}

@Composable
fun MedicineCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left Side: Textual Content
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "PHARMACO INDUSTRIES",
                        style = MaterialTheme.typography.labelMedium,
                        color = SkyBluePrimary,
                        letterSpacing = 1.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Amoxicillin 500mg",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Horizontal List Items (Low Opacity)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        InfoTag(text = "Components")
                        InfoTag(text = "Uses")
                        InfoTag(text = "Effects")
                    }
                }

                // Right Side: Image
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(SkyBlueSecondary)
                ) {
                    // Replace 'R.drawable.medicine' with your actual image resource
                    Image(
                        painter = painterResource(id = android.R.drawable.ic_menu_gallery),
                        contentDescription = "Medicine Image",
                        modifier = Modifier.fillMaxSize().padding(12.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }

            // Faded Horizontal Line
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = Color.LightGray.copy(alpha = 0.4f)
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Star Rating
            Row(verticalAlignment = Alignment.CenterVertically) {
                repeat(4) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFC107), // Gold for stars
                        modifier = Modifier.size(18.dp)
                    )
                }
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color.LightGray,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "4.0 (120 Reviews)",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextGrey
                )
            }
        }
    }
}

@Composable
fun InfoTag(text: String) {
    Surface(
        color = SkyBlueSecondary.copy(alpha = 0.5f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            color = SkyBluePrimary.copy(alpha = 0.8f)
        )
    }
}


@Preview
@Composable
fun PreviewMedicineCard(){
    MedicineSearchScreen( viewModel = MedicineViewModel())
}
