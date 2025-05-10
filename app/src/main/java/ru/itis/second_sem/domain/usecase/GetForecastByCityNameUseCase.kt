package ru.itis.second_sem.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.itis.second_sem.di.qualifiers.IoDispatchers
import ru.itis.second_sem.domain.model.ForecastModel
import ru.itis.second_sem.domain.repository.ForecastRepository
import javax.inject.Inject

class GetForecastByCityNameUseCase @Inject constructor(
    private val forecastRepository: ForecastRepository,
    @IoDispatchers private val ioDispatchers: CoroutineDispatcher
) {

    suspend operator fun invoke(city: String): List<ForecastModel> {
        return withContext(ioDispatchers) {
            forecastRepository.getForecastByCityName(city = city)
        }
    }
}