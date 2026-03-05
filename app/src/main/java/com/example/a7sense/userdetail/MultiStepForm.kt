package com.example.a7sense.userdetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bloodtype
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
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

    var selectedGender by remember { mutableStateOf("") }

    val genders = listOf("Male", "Female", "Other")

    var bloodGroup by remember { mutableStateOf("") }




    var smokes by remember { mutableStateOf(false) }

    var alcoholUse by remember { mutableStateOf("") }
    var smokingStatus by remember{mutableStateOf("")}

    var dietStatus by remember{mutableStateOf("")}



    var state by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }

    val message = viewModel.message.value
    val isSaved = viewModel.isSaved.value

    Scaffold(
        bottomBar = {
            NavigationButtons(
                currentStep = currentStep,
                onNext = { if (currentStep < 2) currentStep++ },
                onBack = { if (currentStep > 0) currentStep-- },
                onFinish = { viewModel.saveUserData(name, age, selectedGender, bloodGroup, alcoholUse, smokingStatus, state, city, dietStatus) },
                isNextEnabled = when(currentStep) {
                    0 -> name.isNotEmpty() && age.isNotEmpty() && selectedGender.isNotEmpty()
                    1 -> bloodGroup.isNotEmpty()
                    else -> state.isNotEmpty() && city.isNotEmpty()
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
        ) {
            // 1. Progress Indicator
            StepProgressIndicator(currentStep = currentStep)

            Spacer(modifier = Modifier.height(32.dp))

            // 2. Animated Content Transition
            AnimatedContent(
                targetState = currentStep,
                transitionSpec = {
                    if (targetState > initialState) {
                        slideInHorizontally { it } + fadeIn() togetherWith slideOutHorizontally { -it } + fadeOut()
                    } else {
                        slideInHorizontally { -it } + fadeIn() togetherWith slideOutHorizontally { it } + fadeOut()
                    }
                }, label = ""
            ) { step ->
                when (step) {
                    0 -> PersonalDetailsStep(
                        name = name, onNameChange = { name = it },
                        age = age, onAgeChange = { age = it },
                        selectedGender = selectedGender, onGenderSelect = { selectedGender = it }
                    )
                    1 -> LifestyleStep(
                        bloodGroup = bloodGroup, onBloodChange = { bloodGroup = it },
                        alcoholUse = alcoholUse, onAlcoholChange = { alcoholUse = it },
                        smokingStatus = smokingStatus, onSmokingChange = { smokingStatus = it },
                        dietStatus = dietStatus, onDietChange = { dietStatus = it }

                    )
                    2 -> LocationStep(
                        state = state, onStateChange = { state = it },
                        city = city, onCityChange = { city = it }
                    )
                }
            }
        }
    }
}
val HealthBlue = Color(0xFF0061A4)
val LightBlue = Color(0xFFD1E4FF)

@Composable
fun StepProgressIndicator(currentStep: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(3) { index ->
            val isActive = index <= currentStep
            val color = if (isActive) HealthBlue else Color.LightGray

            Surface(
                shape = RoundedCornerShape(4.dp),
                color = color,
                modifier = Modifier
                    .weight(1f)
                    .height(6.dp)
                    .padding(horizontal = 4.dp)
            ) {}
        }
    }
}

@Composable
fun NavigationButtons(
    currentStep: Int,
    onNext: () -> Unit,
    onBack: () -> Unit,
    onFinish: () -> Unit,
    isNextEnabled: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (currentStep > 0) {
            OutlinedButton(
                onClick = onBack,
                modifier = Modifier.weight(1f).height(56.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, HealthBlue)
            ) {
                Text("Back", color = HealthBlue)
            }
        }

        Button(
            onClick = if (currentStep < 2) onNext else onFinish,
            modifier = Modifier.weight(2f).height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = HealthBlue),
            enabled = isNextEnabled
        ) {
            Text(if (currentStep < 2) "Continue" else "Finish")
        }
    }
}

// --- SUB-SCREENS ---

@Composable
fun PersonalDetailsStep(name: String, onNameChange: (String) -> Unit, age: String, onAgeChange: (String) -> Unit, selectedGender: String, onGenderSelect: (String) -> Unit) {
    Column {
        FormHeader("Personal Details", "Provide your basic information.")
        CustomTextField(name, onNameChange, "Full Name", Icons.Default.Person)
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(age, onAgeChange, "Age", Icons.Default.Cake, KeyboardType.Number)
        Spacer(modifier = Modifier.height(24.dp))
        Text("Gender", fontWeight = FontWeight.Bold, color = HealthBlue)
        Spacer(modifier = Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("Male", "Female", "Other").forEach { gender ->
                GenderCard(gender, selectedGender == gender, { onGenderSelect(gender) }, Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun LifestyleStep(
    bloodGroup: String,
    onBloodChange: (String) -> Unit,
    alcoholUse: String,
    onAlcoholChange: (String) -> Unit,
    smokingStatus: String,
    onSmokingChange: (String) -> Unit,

    dietStatus: String,
    onDietChange: (String) -> Unit

) {
    Column {
        FormHeader("Lifestyle", "Help us assess your habits.")
        CustomTextField(bloodGroup, onBloodChange, "Blood Group", Icons.Default.Bloodtype)
        Spacer(modifier = Modifier.height(24.dp))

        Text("Smoking Status", fontWeight = FontWeight.Bold, color = HealthBlue)

        SmokingCardSelector(
            selectedValue = smokingStatus,
            onValueChange = { onSmokingChange(it) }
        )

        Text("Diet Status", fontWeight = FontWeight.Bold, color = HealthBlue)

        DietCardSelector(
            selectedValue = dietStatus,
            onValueChange = { onDietChange(it) }
        )

        Text("Alcohol Consumption", fontWeight = FontWeight.Bold, color = HealthBlue)
        AlcoholCardSelector(
            selectedValue = alcoholUse,
            onValueChange = { onAlcoholChange(it) }
        )
    }
}

@Composable
fun LocationStep(state: String, onStateChange: (String) -> Unit, city: String, onCityChange: (String) -> Unit) {
    Column {
        FormHeader("Location", "Where are you located?")
        CustomTextField(state, onStateChange, "State", Icons.Default.Map)
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(city, onCityChange, "City", Icons.Default.LocationCity)
    }
}

// --- REUSABLE COMPONENTS ---

@Composable
fun FormHeader(title: String, subtitle: String) {
    Column(modifier = Modifier.padding(bottom = 24.dp)) {
        Text(title, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = HealthBlue)
        Text(subtitle, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
    }
}

@Composable
fun CustomTextField(value: String, onValueChange: (String) -> Unit, label: String, icon: ImageVector, keyboardType: KeyboardType = KeyboardType.Text) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = { Icon(icon, contentDescription = null, tint = HealthBlue) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = HealthBlue,
            focusedLabelColor = HealthBlue
        )
    )
}

@Composable
fun GenderCard(
    label: String,
    isSelected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    val containerColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.primaryContainer
        else MaterialTheme.colorScheme.surface
    )
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.outlineVariant
    )

    Surface(
        modifier = modifier
            .height(60.dp)
            .clickable { onSelect() },
        shape = RoundedCornerShape(12.dp),
        color = containerColor,
        border = BorderStroke(1.dp, borderColor)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                color = if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurface,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}



@Composable
fun DietCardSelector(
    selectedValue: String,
    onValueChange: (String) -> Unit
) {
    val options = listOf("Vegetarian", "Non-Vegetarian")

    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = "Smoking Status",
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0061A4), // HealthBlue
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            options.forEach { option ->
                val isSelected = selectedValue == option

                // Animated colors for the card
                val containerColor by animateColorAsState(
                    targetValue = if (isSelected) Color(0xFFD1E4FF) else MaterialTheme.colorScheme.surface
                )
                val contentColor by animateColorAsState(
                    targetValue = if (isSelected) Color(0xFF0061A4) else Color.Gray
                )

                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .clickable { onValueChange(option) }, // This updates the state
                    shape = RoundedCornerShape(12.dp),
                    color = containerColor,
                    border = BorderStroke(
                        width = 1.dp,
                        color = if (isSelected) Color(0xFF0061A4) else Color.LightGray
                    )
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = option,
                            color = contentColor,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}



@Composable
fun AlcoholCardSelector(
    selectedValue: String,
    onValueChange: (String) -> Unit
) {
    val options = listOf("None", "Regular", "Occasional","Heavy")

    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = "Smoking Status",
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0061A4), // HealthBlue
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            options.forEach { option ->
                val isSelected = selectedValue == option

                // Animated colors for the card
                val containerColor by animateColorAsState(
                    targetValue = if (isSelected) Color(0xFFD1E4FF) else MaterialTheme.colorScheme.surface
                )
                val contentColor by animateColorAsState(
                    targetValue = if (isSelected) Color(0xFF0061A4) else Color.Gray
                )

                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .clickable { onValueChange(option) }, // This updates the state
                    shape = RoundedCornerShape(12.dp),
                    color = containerColor,
                    border = BorderStroke(
                        width = 1.dp,
                        color = if (isSelected) Color(0xFF0061A4) else Color.LightGray
                    )
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = option,
                            color = contentColor,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SmokingCardSelector(
    selectedValue: String,
    onValueChange: (String) -> Unit
) {
    val options = listOf("Never", "Former", "Current")

    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = "Smoking Status",
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0061A4), // HealthBlue
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            options.forEach { option ->
                val isSelected = selectedValue == option

                // Animated colors for the card
                val containerColor by animateColorAsState(
                    targetValue = if (isSelected) Color(0xFFD1E4FF) else MaterialTheme.colorScheme.surface
                )
                val contentColor by animateColorAsState(
                    targetValue = if (isSelected) Color(0xFF0061A4) else Color.Gray
                )

                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .clickable { onValueChange(option) }, // This updates the state
                    shape = RoundedCornerShape(12.dp),
                    color = containerColor,
                    border = BorderStroke(
                        width = 1.dp,
                        color = if (isSelected) Color(0xFF0061A4) else Color.LightGray
                    )
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = option,
                            color = contentColor,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 14.sp
                        )
                    }
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