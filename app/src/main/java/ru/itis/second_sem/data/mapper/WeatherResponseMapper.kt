package ru.itis.second_sem.data.mapper

import ru.itis.second_sem.data.remote.pojo.CurrentWeatherResponse
import ru.itis.second_sem.domain.model.WeatherModel
import javax.inject.Inject

class WeatherResponseMapper @Inject constructor() { //преобразуем мз ответа (responce) в готовую модельку
    fun map(input: CurrentWeatherResponse?) : WeatherModel {
        return input?.let {
            WeatherModel(
                currentTemp = it.mainData?.temp ?: -99F,
                weatherDescription = it.weather?.firstOrNull()?.description ?: ""
            )
        } ?: WeatherModel(
            currentTemp = -99F,
            weatherDescription = ""
        )
    }
}