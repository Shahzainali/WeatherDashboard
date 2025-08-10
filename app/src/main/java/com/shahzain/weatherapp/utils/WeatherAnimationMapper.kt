package com.shahzain.weatherapp.utils

import androidx.annotation.RawRes
import com.shahzain.weatherapp.R

object WeatherAnimationMapper {

    @RawRes
    fun getAnimationForWeather(weatherCondition: String, iconCode: String = ""): Int {
        return when (weatherCondition.lowercase()) {
            "clear" -> if (iconCode.contains("d")) R.raw.sunny else R.raw.night

            "clouds" -> R.raw.clouds
            "rain" -> R.raw.rain
            "drizzle" -> R.raw.rain
            "snow" -> R.raw.snow
            "thunderstorm" -> R.raw.thunder
            "mist", "smoke", "haze", "dust", "fog", "sand", "ash" -> R.raw.mist_fog
            "squall", "tornado" -> R.raw.mist_fog

            else -> R.raw.sunny
        }
    }
}