package com.example.temperatureconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.temperatureconverter.ui.theme.TemperatureConverterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TemperatureConverterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TemperatureConverterScreen()
                }
            }
        }
    }
}

@Composable
fun TemperatureConverterScreen() {
    // Remembered states (variables that survive UI refreshes)
    var input by remember { mutableStateOf(TextFieldValue("")) }
    var result by remember { mutableStateOf("") }

    // Main screen layout: Column = vertical arrangement
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "🌡️ Temperature Converter",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = input,
            onValueChange = { input = it },
            label = { Text("Enter °C") },
            singleLine = true
        )

        Button(
            onClick = {
                val celsius = input.text.toDoubleOrNull()
                if (celsius != null) {
                    val fahrenheit = (celsius * 9 / 5) + 32
                    val kelvin = celsius + 273.15
                    result = "Fahrenheit: ${"%.2f".format(fahrenheit)} °F\n" +
                            "Kelvin: ${"%.2f".format(kelvin)} K"
                } else {
                    result = "Please enter a valid number"
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Convert")
        }

        if (result.isNotEmpty()) {
            Text(
                text = result,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 24.dp)
            )
        }
    }
}
