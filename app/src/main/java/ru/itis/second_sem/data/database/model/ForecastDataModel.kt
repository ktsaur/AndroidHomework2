package ru.itis.second_sem.data.database.model

import kotlinx.serialization.Serializable

@Serializable
data class ForecastDataModel(
    val dt: String,
    val temp: Float,
    val feelsLike: Float,
    val tempMin: Float,
    val tempMax: Float,
    val mainDesc: String,
    val description: String
)