package ru.itis.second_sem.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.itis.second_sem.domain.model.WeatherModel
import ru.itis.second_sem.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherByCityNameUseCase @Inject constructor (
    private val weatherRepository: WeatherRepository,
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(city: String): WeatherModel {
        return withContext(ioDispatcher) {
            weatherRepository.getWeatherByCityName(city = city)
        }

    }
}