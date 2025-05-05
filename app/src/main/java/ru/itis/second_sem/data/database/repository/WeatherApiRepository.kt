package ru.itis.second_sem.data.database.repository

import ru.itis.second_sem.data.database.dao.WeatherApiDao
import ru.itis.second_sem.data.database.entity.WeatherApiEntity
import ru.itis.second_sem.data.database.model.ForecastDataModel
import ru.itis.second_sem.data.database.model.WeatherDataModel
import javax.inject.Inject

class WeatherApiRepository @Inject constructor(
    private val weatherApiDao: WeatherApiDao
){
    suspend fun getWeatherApi(city: String): WeatherApiEntity {
        return weatherApiDao.getWeatherApi(city = city) ?: throw IllegalStateException("Weather not found")
    }

    suspend fun saveWeatherApi(weatherApiEntity: WeatherApiEntity) {
        weatherApiDao.saveWeatherApi(weatherApiEntity = weatherApiEntity)
    }

    suspend fun updateWeatherApi(city: String, currentTemp: WeatherDataModel, forecast: List<ForecastDataModel>) {
        weatherApiDao.updateWeatherApi(city = city, currentTemp = currentTemp, forecast = forecast)
    }
}