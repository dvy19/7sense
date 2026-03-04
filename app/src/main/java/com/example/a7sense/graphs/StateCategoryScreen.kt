package com.example.a7sense.graphs

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
@Composable
fun StateCategoryScreen(viewModel: StateCategoryViewModel = viewModel()) {

    val stateData by viewModel.stateData.collectAsState()
    val states by viewModel.states.collectAsState()


    var selectedState by remember { mutableStateOf("West Bengal") }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        if (states.isNotEmpty()) {

            StateDropdown(
                states = states,
                selectedState = selectedState,
                onStateSelected = {
                    selectedState = it
                    viewModel.fetchState(it)
                }
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        // -------- Disease List ----------
        stateData.forEach { (disease, count) ->
            Text(text = "$disease : $count")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // -------- Chart ----------
        if (stateData.isNotEmpty()) {
            BarChartView(stateData)
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

    Box {
        Button(onClick = { expanded = true }) {
            Text(selectedState)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
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
fun BarChartView(data: Map<String, Long>) {

    AndroidView(
        factory = { context ->
            BarChart(context).apply {

                // ---- Convert Data ----
                val entries = data.entries.mapIndexed { index, entry ->
                    BarEntry(index.toFloat(), entry.value.toFloat())
                }

                val dataSet = BarDataSet(entries, "Disease Count")

                // ✅ 1. Different colors for bars
                dataSet.colors = listOf(
                    Color.BLUE,
                    Color.RED,
                    Color.GREEN,
                    Color.MAGENTA,
                    Color.CYAN
                )

                // ✅ 2. Reduce bar width
                val barData = BarData(dataSet)
                barData.barWidth = 0.3f   // smaller = thinner bars

                this.data = barData

                // ✅ 3. Add spacing & padding
                setFitBars(true)
                setExtraOffsets(30f, 30f, 30f, 30f)

                // ✅ Disable description
                description.isEnabled = false

                // ✅ Improve X Axis
                xAxis.apply {
                    position = XAxis.XAxisPosition.BOTTOM
                    granularity = 1f
                    setDrawGridLines(false)

                    // Show disease names on X axis
                    valueFormatter = IndexAxisValueFormatter(
                        data.keys.toList()
                    )
                }

                xAxis.labelRotationAngle = -45f
                axisRight.isEnabled = false
                axisLeft.axisMinimum = 0f

                animateY(1000)

                invalidate()
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)  // Increased height
            .padding(16.dp)  // Outer padding
    )
}
@Preview
@Composable
fun PreviewStateCategoryScreen() {
    StateCategoryScreen()
}