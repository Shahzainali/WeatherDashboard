package com.shahzain.weatherapp.data.repository

import com.shahzain.weatherapp.data.api.WeatherApi
import com.shahzain.weatherapp.data.model.WeatherData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

sealed class NetworkResult<T> {
    class Success<T>(val data: T) : NetworkResult<T>()
    class Error<T>(val message: String) : NetworkResult<T>()
    class Loading<T> : NetworkResult<T>()
}

@Singleton
class WeatherRepository @Inject constructor(
    private val weatherApi: WeatherApi
) {
    fun getWeather(cityName: String): Flow<NetworkResult<WeatherData>> = flow {
        emit(NetworkResult.Loading())
        
        try {
            val response = weatherApi.getCurrentWeather(
                cityName = cityName,
                apiKey = WeatherApi.API_KEY
            )
            
            if (response.isSuccessful && response.body() != null) {
                val weatherResponse = response.body()!!
                print(weatherResponse)
                val weatherData = WeatherData(
                    cityName = weatherResponse.name,
                    temperature = weatherResponse.main.temp.toInt(),
                    condition = weatherResponse.weather[0].main,
                    description = weatherResponse.weather[0].description,
                    iconCode = weatherResponse.weather[0].icon
                )
                emit(NetworkResult.Success(weatherData))
            } else {
                emit(NetworkResult.Error("City not found"))
            }
        } catch (e: Exception) {
            val message = when (e) {
                is UnknownHostException -> "No internet connection. Please check your network and try again."
                else -> "Something went wrong. Please try again later."
            }
            emit(NetworkResult.Error(message))        }
    }
}