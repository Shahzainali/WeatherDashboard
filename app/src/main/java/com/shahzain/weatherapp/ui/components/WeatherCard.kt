package com.shahzain.weatherapp.ui.components


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shahzain.weatherapp.data.model.WeatherData
import com.shahzain.weatherapp.ui.theme.CardBackgound
import com.shahzain.weatherapp.ui.theme.TextColor

@Composable
fun WeatherCard(
    weatherData: WeatherData,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBackgound
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = weatherData.cityName,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = TextColor
            )
            
            Spacer(modifier = Modifier.height(16.dp))

            WeatherAnimationView(
                weatherCondition = weatherData.condition,
                iconCode = weatherData.iconCode,
                size = 160.dp
            )
            
            Text(
                text = "${weatherData.temperature}°C",
                fontSize = 48.sp,
                fontWeight = FontWeight.Light,
                color = TextColor
            )
            
            Text(
                text = weatherData.condition,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = TextColor
            )
            
            Text(
                text = weatherData.description.replaceFirstChar { it.uppercase() },
                fontSize = 14.sp,
                color = TextColor.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
fun LoadingCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
                containerColor = CardBackgound
                )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun ErrorCard(
    errorMessage: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Error",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onErrorContainer
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = errorMessage,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onErrorContainer
            )
        }
    }
}