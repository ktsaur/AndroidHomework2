package ru.itis.second_sem.presentation.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.itis.second_sem.data.database.InceptionDatabase
import ru.itis.second_sem.data.database.entity.QueryHistoryEntity
import ru.itis.second_sem.data.database.entity.WeatherApiEntity
import ru.itis.second_sem.domain.usecase.GetForecastByCityNameUseCase
import ru.itis.second_sem.domain.usecase.GetWeatherByCityNameUseCase
import ru.itis.second_sem.presentation.uiState.WeatherUIState
import ru.itis.second_sem.presentation.utils.CityValidationException
import ru.itis.second_sem.presentation.utils.toData
import ru.itis.second_sem.presentation.utils.toDomain
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class TempDetailsViewModel @Inject constructor(
    private val getForecastByCityNameUseCase: GetForecastByCityNameUseCase,
    private val getWeatherByCityNameUseCase: GetWeatherByCityNameUseCase,
    private val database: InceptionDatabase
) : ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUIState())
    val uiState = _uiState.asStateFlow()

    fun getForecast(city: String) {
        if (city.firstOrNull()?.isLowerCase() == true) {
            _uiState.update { it.copy(error = CityValidationException()) }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(error = null) }

            val lastTimestep = withContext(Dispatchers.IO) {
                database.queryHistoryDao.getLastQueryForCity(city = city)
            }

            val shouldFetchFromApi = withContext(Dispatchers.IO) {
                if (lastTimestep == null) {
                    return@withContext true
                }
                val countBetween =
                    database.queryHistoryDao.countQueryBetween(
                        start = lastTimestep,
                        end = System.currentTimeMillis()
                    )
                Log.d("TIMESTAMPS", "timestamp = $lastTimestep")
                Log.d("countQueryBetween", "$countBetween")
                return@withContext (abs(lastTimestep - System.currentTimeMillis()) >= 5 * 60 * 1000 || countBetween >= 3)
            }

            if (shouldFetchFromApi) {
                fetchFromApi(city = city)
            } else {
                fetchFromDb(city = city)
            }
            /*val weatherApiEntity =
                withContext(Dispatchers.IO) { database.weatherApiDao.getWeatherApi(city = city) }
            if (weatherApiEntity == null || currentTime - weatherApiEntity.timestamp >= 5 * 60 * 1000) { //+ условие что между текущим и прошлым три и более городов
                runCatching {
                    val forecast = getForecastByCityNameUseCase.invoke(city = city)
                    val weather = getWeatherByCityNameUseCase.invoke(city = city)
                    Pair(forecast, weather)
                }.onSuccess { (forecast, weather) ->
                    _uiState.update {
                        it.copy(
                            forecast = forecast,
                            weather = weather,
                            error = null
                        )
                    }
                    withContext(Dispatchers.IO) {
                        val weatherApiEntity =
                            WeatherApiEntity(
                                city = city,
                                currentTemp = weather.toData(),
                                forecast = forecast.map { it.toData() },
                                timestamp = System.currentTimeMillis()
                            )
                        database.weatherApiDao.saveWeatherApi(weatherApiEntity)
                    }
                }.onFailure { ex ->
                    _uiState.update { it.copy(error = ex) }
                }
            } else {
                _uiState.update {
                    it.copy(
                        forecast = weatherApiEntity.forecast.toDomain(),
                        weather = weatherApiEntity.currentTemp.toDomain(),
                        error = null
                    )
                }
            }
        }*/
        }
    }

    private suspend fun insertQueryHistory(query: QueryHistoryEntity) {
        withContext(Dispatchers.IO) { database.queryHistoryDao.insertQuery(queryHistoryEntity = query) }
    }

    private suspend fun fetchFromApi(city: String) {
        runCatching {
            val forecast = getForecastByCityNameUseCase.invoke(city = city)
            val weather = getWeatherByCityNameUseCase.invoke(city = city)
            Pair(forecast, weather)
        }.onSuccess { (forecast, weather) ->
            _uiState.update {
                it.copy(
                    forecast = forecast,
                    weather = weather,
                    error = null
                )
            }
            withContext(Dispatchers.IO) {
                val weatherApiEntity =
                    WeatherApiEntity(
                        city = city,
                        currentTemp = weather.toData(),
                        forecast = forecast.map { it.toData() },
                    )
                database.weatherApiDao.insertWeatherApi(weatherApiEntity)
            }
            insertQueryHistory(
                QueryHistoryEntity(
                    city = city,
                    timestamp = System.currentTimeMillis()
                )
            )
        }.onFailure { ex ->
            _uiState.update { it.copy(error = ex) }
        }
    }

    private suspend fun fetchFromDb(city: String) {
        withContext(Dispatchers.IO) {
            database.weatherApiDao.getWeatherApi(city)
        }?.let { weatherApiEntity ->
            _uiState.update {
                it.copy(
                    forecast = weatherApiEntity.forecast.toDomain(),
                    weather = weatherApiEntity.currentTemp.toDomain(),
                    error = null
                )
            }
            insertQueryHistory(
                QueryHistoryEntity(
                    city = city,
                    timestamp = System.currentTimeMillis()
                )
            )
        }
    }

}
