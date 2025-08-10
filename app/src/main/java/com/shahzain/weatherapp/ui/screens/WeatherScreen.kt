package com.shahzain.weatherapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shahzain.weatherapp.ui.components.ErrorCard
import com.shahzain.weatherapp.ui.components.LoadingCard
import com.shahzain.weatherapp.ui.components.WeatherCard
import com.shahzain.weatherapp.ui.theme.ButtonColor
import com.shahzain.weatherapp.ui.theme.DisabledButtonColor
import com.shahzain.weatherapp.ui.theme.TextColor
import com.shahzain.weatherapp.ui.theme.TextColorDark
import com.shahzain.weatherapp.viewmodel.WeatherViewModel

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var cityName by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Weather Dashboard",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(vertical = 32.dp)
        )

        OutlinedTextField(
            value = cityName,
            onValueChange = { cityName = it },
            label = { Text("Enter city name") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    viewModel.searchWeather(cityName)
                    keyboardController?.hide()
                }
            ),
            singleLine = true
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(
            onClick = {
                viewModel.searchWeather(cityName)
                keyboardController?.hide()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = cityName.isNotBlank() && !uiState.isLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = ButtonColor,
                contentColor = TextColor,
                disabledContainerColor = DisabledButtonColor,
            )
        ) {
            Text("Search Weather")
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        when {
            uiState.isLoading -> {
                LoadingCard()
            }
            uiState.errorMessage != null -> {
                ErrorCard(errorMessage = uiState.errorMessage!!)
            }
            uiState.weatherData != null -> {
                WeatherCard(weatherData = uiState.weatherData!!)
            }
        }
    }
}