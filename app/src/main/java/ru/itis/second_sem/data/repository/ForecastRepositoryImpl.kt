package ru.itis.second_sem.data.repository

import ru.itis.second_sem.data.mapper.ForecastResponseMapper
import ru.itis.second_sem.data.remote.OpenWeatherApi
import ru.itis.second_sem.domain.model.ForecastModel
import ru.itis.second_sem.domain.repository.ForecastRepository
import javax.inject.Inject

class ForecastRepositoryImpl @Inject constructor(
    private val weatherApi: OpenWeatherApi,
    private val mapper: ForecastResponseMapper
) : ForecastRepository {

    override suspend fun getForecastByCityName(city: String): List<ForecastModel> {
       return weatherApi.getForecastWeatherByCityName(city = city).let(mapper::map)
    }
}