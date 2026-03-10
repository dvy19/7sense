package com.example.a7sense.graphs.city

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*

import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
// MPAndroidChart imports (Assumed based on your snippet)

import com.example.a7sense.graphs.StateCategoryViewModel
import kotlin.collections.component1
import kotlin.collections.component2
import androidx.compose.runtime.Composable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
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
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import android.graphics.Color
import androidx.compose.foundation.shape.RoundedCornerShape
@Composable
fun CityNameScreen(
    mainNavController: NavController,
    viewModel: CityNameViewModel = viewModel()
) {

    val cityData by viewModel.cityData.collectAsState()
    val states by viewModel.cities.collectAsState()
    var selectedCity by remember { mutableStateOf("Bhopal") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        // -------- Dropdown ----------
        if (states.isNotEmpty()) {
            StateDropdown(
                states = states,
                selectedState = selectedCity,
                onStateSelected = {
                    selectedCity = it
                    viewModel.GetCityData(it)
                }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // -------- Disease List ----------
        cityData.forEach { (disease, count) ->
            Text(text = "$disease : $count")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // -------- Chart ----------
        if (cityData.isNotEmpty()) {
            ImprovedBarChartView(cityData)
        }
    }
}
@Composable
fun StateDropdown(
    states: List<String>,
    selectedState: String,
    onStateSelected: (String) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }

    OutlinedCard(
        onClick = { expanded = true },
        shape = RoundedCornerShape(12.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = if (selectedState.isEmpty()) "Select State" else selectedState
            )

            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            //modifier = Modifier.background(Color.WHITE)
        ) {
            states.forEach { state ->
                DropdownMenuItem(
                    text = { Text(state) },
                    onClick = {
                        onStateSelected(state)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun ImprovedBarChartView(data: Map<String, Long>) {
    AndroidView(
        factory = { context ->
            BarChart(context).apply {
                val entries = data.entries.mapIndexed { i, entry -> BarEntry(i.toFloat(), entry.value.toFloat()) }
                val dataSet = BarDataSet(entries, "Reported Cases").apply {
                    // Professional single-tone blue gradient or shades
                    colors = listOf(
                        android.graphics.Color.parseColor("#42A5F5"),
                        android.graphics.Color.parseColor("#1E88E5"),
                        android.graphics.Color.parseColor("#1565C0")
                    )
                    valueTextColor = android.graphics.Color.BLACK
                    valueTextSize = 10f
                }

                this.data = BarData(dataSet).apply { barWidth = 0.4f }

                description.isEnabled = false
                legend.isEnabled = false
                setFitBars(true)

                xAxis.apply {
                    position = XAxis.XAxisPosition.BOTTOM
                    setDrawGridLines(false)
                    granularity = 1f
                    valueFormatter = IndexAxisValueFormatter(data.keys.toList())
                }

                axisLeft.apply {
                    setDrawGridLines(true)
                    gridColor = android.graphics.Color.LTGRAY
                    axisLineColor = android.graphics.Color.TRANSPARENT
                }
                axisRight.isEnabled = false
                animateY(800)
            }
        },
        modifier = Modifier.fillMaxSize().padding(16.dp)
    )
}

@Preview
@Composable
fun PreviewStateCategoryScreen() {
    CityNameScreen(mainNavController = rememberNavController())
}