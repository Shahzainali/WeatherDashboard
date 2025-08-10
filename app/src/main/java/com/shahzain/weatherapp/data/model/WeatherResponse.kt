package com.shahzain.weatherapp.data.model

data class WeatherResponse(
    val name: String,
    val main: Main,
    val weather: List<Weather>,
    val cod: Int
)

data class Main(
    val temp: Double,
    val feels_like: Double,
    val humidity: Int
)

data class Weather(
    val main: String,
    val description: String,
    val icon: String
)

data class WeatherData(
    val cityName: String,
    val temperature: Int,
    val condition: String,
    val description: String,
    val iconCode: String
)