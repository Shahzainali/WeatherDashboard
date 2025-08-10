package com.shahzain.weatherapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.shahzain.weatherapp.utils.WeatherAnimationMapper

@Composable
fun WeatherAnimationView(
    weatherCondition: String,
    iconCode: String = "",
    modifier: Modifier = Modifier,
    size: Dp = 200.dp
) {
    val animationRes = WeatherAnimationMapper.getAnimationForWeather(weatherCondition, iconCode)

    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(animationRes)
    )

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        speed = 0.8f,
        restartOnPlay = true
    )
    
    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.fillMaxSize()
        )
    }
}