package ru.itis.second_sem.domain.repository

import ru.itis.second_sem.domain.model.ForecastModel

interface ForecastRepository {

    suspend fun getForecastByCityName(city: String): List<ForecastModel>
}