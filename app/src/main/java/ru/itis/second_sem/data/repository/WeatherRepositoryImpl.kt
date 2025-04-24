package ru.itis.second_sem.data.repository

import ru.itis.second_sem.data.mapper.WeatherResponseMapper
import ru.itis.second_sem.data.remote.OpenWeatherApi
import ru.itis.second_sem.domain.model.WeatherModel
import ru.itis.second_sem.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherAPI: OpenWeatherApi,
    private val mapper: WeatherResponseMapper
): WeatherRepository {

    override suspend fun getWeatherByCityName(city: String): WeatherModel {
        return weatherAPI.getCurrentWeatherByCityName(city = city).let(mapper::map)
    }
}