package ru.itis.second_sem.data.database.model

import kotlinx.serialization.Serializable

@Serializable
data class WeatherDataModel(
    val currentTemp: Float,
    val description: String
)