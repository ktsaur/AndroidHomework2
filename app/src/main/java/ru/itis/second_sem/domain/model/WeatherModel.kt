package ru.itis.second_sem.domain.model

import androidx.compose.runtime.Immutable

data class WeatherModel(
    val currentTemp: Float,
    val weatherDescription: String
) {
    companion object{
        val EMPTY = WeatherModel(
            currentTemp = 0.0f,
            weatherDescription = "",
        )
    }
}

