package com.example.a7sense.graphs
import androidx.compose.runtime.Composable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.a7sense.auth.AuthViewModel
import com.example.a7sense.ui.theme._7SenseTheme
import com.google.firebase.firestore.FirebaseFirestore
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet


@Composable
fun AgeVsDiseaseGraph(){

    val db= FirebaseFirestore.getInstance();

    var selectedAge by remember { mutableStateOf("21-30") }
    var diseaseData by remember { mutableStateOf<Map<String, Any>>(emptyMap()) }

    // Fetch data when age changes
    LaunchedEffect(selectedAge) {
        db.collection("age_stats")
            .document(selectedAge)
            .get()
            .addOnSuccessListener { document ->
                document.data?.let {
                    diseaseData = it
                }
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        AgeDropdown(selectedAge) {
            selectedAge = it
        }

        Spacer(modifier = Modifier.height(20.dp))

        DiseaseBarChart(diseaseData)
    }

}

@Composable
fun AgeDropdown(
    selectedAge: String,
    onAgeSelected: (String) -> Unit
) {

    val ageGroups = listOf("21-30","31-40","41-50","51-60")

    var expanded by remember { mutableStateOf(false) }

    Box {

        OutlinedButton(onClick = { expanded = true }) {
            Text(selectedAge)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            ageGroups.forEach { age ->
                DropdownMenuItem(
                    text = { Text(age) },
                    onClick = {
                        onAgeSelected(age)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun DiseaseBarChart(dataMap: Map<String, Any>) {

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp),
        factory = { context ->

            BarChart(context).apply {

                val entries = ArrayList<BarEntry>()
                var index = 0f

                dataMap.forEach { (disease, count) ->

                    if (disease != "total" && disease != "most_common") {

                        entries.add(
                            BarEntry(index, (count as Long).toFloat())
                        )
                        index++
                    }
                }

                val dataSet = BarDataSet(entries, "Top Diseases")
                val barData = BarData(dataSet)

                this.data = barData
                invalidate()
            }
        },
        update = { chart ->

            val entries = ArrayList<BarEntry>()
            var index = 0f

            dataMap.forEach { (disease, count) ->

                if (disease != "total" && disease != "most_common") {

                    entries.add(
                        BarEntry(index, (count as Long).toFloat())
                    )
                    index++
                }
            }

            chart.data = BarData(BarDataSet(entries, "Top Diseases"))
            chart.invalidate()
        }
    )
}

@Preview
@Composable
fun PreviewAgeVsDisease(){

    AgeVsDiseaseGraph()

}