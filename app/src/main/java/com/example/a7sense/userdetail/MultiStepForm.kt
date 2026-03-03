package com.example.a7sense.userdetail

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun MultiStepForm(rootNavController: NavController) {

    val context = LocalContext.current

    val viewModel: UserViewModel = viewModel()


    var currentStep by remember { mutableStateOf(0) }


    // Store user inputs
    var age by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }

    var gender by remember { mutableStateOf("") }

    var bloodGroup by remember { mutableStateOf("") }




    var smokes by remember { mutableStateOf(false) }

    var alcoholUse by remember { mutableStateOf("None") }
    var smokingStatus by remember{mutableStateOf("None")}


    var state by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }

    val message = viewModel.message.value
    val isSaved = viewModel.isSaved.value

    Column(modifier = Modifier.padding(16.dp)) {
        when (currentStep) {
            0 -> {
                Text("Step 1: Age & Gender")
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") }
                )
                OutlinedTextField(
                    value = age,
                    onValueChange = { age = it },
                    label = { Text("Age") }
                )
                OutlinedTextField(
                    value = gender,
                    onValueChange = { gender = it },
                    label = { Text("Gender") }
                )
            }


            1 -> {
                Text("Step 2: Lifestyle")

                OutlinedTextField(
                    value =  bloodGroup,
                    onValueChange = {bloodGroup = it },
                    label = { Text("Blood Group") }
                )


                AlcoholCardSelector(
                    selectedValue = alcoholUse,
                    onValueChange = { alcoholUse = it }
                )

                SmokingCardSelector(
                    selectedValue = smokingStatus,
                    onValueChange = { smokingStatus = it }
                )



            }

            2 -> {
                Text("Step 3: Location")
                OutlinedTextField(
                    value = state,
                    onValueChange = { state = it },
                    label = { Text("State") }
                )
                OutlinedTextField(
                    value = city,
                    onValueChange = { city = it },
                    label = { Text("City") }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            if (currentStep > 0) {
                Button(onClick = { currentStep-- }) {
                    Text("Back")
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            if (currentStep < 2) {
                Button(onClick = { currentStep++ }) {
                    Text("Next")
                }
            } else {
                Button(onClick = {
                    // Handle final submission
                    //println("Age: $age, Gender: $gender, Drinks: $alcoholUse, Smokes: $smokes, State: $state, City: $city")

                    viewModel.saveUserData(name, age, gender, bloodGroup, alcoholUse, smokingStatus, state, city)


                }) {
                    Text("Finish")
                }
            }
        }

        LaunchedEffect(isSaved) {
            if (isSaved) {
                rootNavController.navigate("main") {
                    popUpTo("login") { inclusive = true }
                }
            }
        }
    }
}
@Composable
fun AlcoholCardSelector(selectedValue: String, onValueChange: (String) -> Unit) {
    val options = listOf("None", "Occasional", "Heavy", "Regular")

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        options.forEach { option ->
            val isSelected = option == selectedValue

            Card(
                modifier = Modifier
                    .width(90.dp)
                    .height(50.dp)
                    .clickable { onValueChange(option) },
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) Color(0xFF6200EE) else Color.LightGray
                ),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = option,
                        color = if (isSelected) Color.White else Color.Black,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}



@Composable
fun SmokingCardSelector(selectedValue: String, onValueChange: (String) -> Unit) {
    val options = listOf("Current", "Former", "Never")

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        options.forEach { option ->
            val isSelected = option == selectedValue

            Card(
                modifier = Modifier
                    .width(90.dp)
                    .height(50.dp)
                    .clickable { onValueChange(option) },
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) Color(0xFF6200EE) else Color.LightGray
                ),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = option,
                        color = if (isSelected) Color.White else Color.Black,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewMultiStepForm(){

    MultiStepForm(rootNavController = rememberNavController())

}