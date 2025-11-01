package com.example.temperatureconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.temperatureconverter.ui.theme.TemperatureConverterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TemperatureConverterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black // 🖤 Dark background
                ) {
                    TemperatureConverterApp()
                }
            }
        }
    }
}

@Composable
fun TemperatureConverterApp() {
    var inputValue by remember { mutableStateOf(TextFieldValue("")) }
    var fromUnit by remember { mutableStateOf("Celsius") }
    var toUnit by remember { mutableStateOf("Fahrenheit") }
    var result by remember { mutableStateOf("") }

    val units = listOf("Celsius", "Fahrenheit", "Kelvin")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "🌡️ Temperature Converter",
            color = Color.White,
            fontSize = 22.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = inputValue,
            onValueChange = { inputValue = it },
            label = { Text("Enter value", color = Color.White) },
            textStyle = LocalTextStyle.current.copy(color = Color.White),
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray
            )
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            UnitDropdown("From", fromUnit, units) { fromUnit = it }
            UnitDropdown("To", toUnit, units) { toUnit = it }
        }

        Button(
            onClick = {
                result = convertTemperature(inputValue.text, fromUnit, toUnit)
            },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
        ) {
            Text("Convert", color = Color.White)
        }

        if (result.isNotEmpty()) {
            Text(
                text = "Result: $result $toUnit",
                color = Color.Cyan,
                fontSize = 20.sp,
                modifier = Modifier.padding(top = 20.dp)
            )
        }
    }
}

@Composable
fun UnitDropdown(label: String, selectedUnit: String, options: List<String>, onSelect: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(text = label, color = Color.Gray, fontSize = 14.sp)
        Box {
            Button(
                onClick = { expanded = true },
                modifier = Modifier.padding(vertical = 4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
            ) {
                Text(selectedUnit, color = Color.White)
            }

            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                options.forEach { unit ->
                    DropdownMenuItem(
                        text = { Text(unit) },
                        onClick = {
                            onSelect(unit)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

fun convertTemperature(value: String, from: String, to: String): String {
    val temp = value.toDoubleOrNull() ?: return "Invalid input"

    val celsius = when (from) {
        "Celsius" -> temp
        "Fahrenheit" -> (temp - 32) * 5 / 9
        "Kelvin" -> temp - 273.15
        else -> temp
    }

    val result = when (to) {
        "Celsius" -> celsius
        "Fahrenheit" -> (celsius * 9 / 5) + 32
        "Kelvin" -> celsius + 273.15
        else -> celsius
    }

    return String.format("%.2f", result)
}
