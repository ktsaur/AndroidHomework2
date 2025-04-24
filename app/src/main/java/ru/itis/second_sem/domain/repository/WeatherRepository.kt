package ru.itis.second_sem.domain.repository

import ru.itis.second_sem.domain.model.WeatherModel

interface WeatherRepository {

    suspend fun getWeatherByCityName(city: String): WeatherModel
}