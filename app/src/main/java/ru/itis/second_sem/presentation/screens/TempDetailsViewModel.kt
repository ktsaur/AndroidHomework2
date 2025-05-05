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
import ru.itis.second_sem.data.database.entity.WeatherApiEntity
import ru.itis.second_sem.data.mapper.toData
import ru.itis.second_sem.data.mapper.toDomain
import ru.itis.second_sem.domain.model.ForecastModel
import ru.itis.second_sem.domain.model.WeatherModel
import ru.itis.second_sem.domain.usecase.GetForecastByCityNameUseCase
import ru.itis.second_sem.domain.usecase.GetWeatherByCityNameUseCase
import ru.itis.second_sem.presentation.uiState.WeatherUIState
import ru.itis.second_sem.presentation.utils.CityValidationException
import javax.inject.Inject

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
        } else {
            _uiState.update { it.copy(error = null) }
            viewModelScope.launch {
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
//                    saveWeatherResponse(city = city, weather = weather, forecast = forecast)
                }.onFailure { ex ->
                    _uiState.update { it.copy(error = ex) }
                }
            }
        }
    }


    fun getForecast2(city: String) {
        if (city.firstOrNull()?.isLowerCase() == true) {
            _uiState.update { it.copy(error = CityValidationException()) }
        } else {
            viewModelScope.launch {
                _uiState.update { it.copy(error = null) }
                val weatherApiEntity =
                    withContext(Dispatchers.IO) { database.weatherApiDao.getWeatherApi(city = city) }
                val currentTime = System.currentTimeMillis()
                if (weatherApiEntity == null || currentTime - weatherApiEntity.timestamp >= 5 * 60 * 1000) {
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
            }
        }
    }

  /*  fun saveWeatherResponse(city: String, weather: WeatherModel, forecast: List<ForecastModel>) {
        viewModelScope.launch {
            val weatherData = weather.toData()
            val forecastData = forecast.map { it.toData() }
            val weatherApiEntity =
                WeatherApiEntity(
                    city = city,
                    currentTemp = weatherData,
                    forecast = forecastData,
                    timestamp =
                )
            database.weatherApiDao.saveWeatherApi(weatherApiEntity)
        }
    }*/


    /*fun getCurrentTemp(city: String) {
            _uiState.update { it?.copy(error = null) }
            viewModelScope.launch {
                runCatching {
                    getWeatherByCityNameUseCase.invoke(city = city)
                }.onSuccess {  weatherModel ->
                    _uiState.update { it?.copy(weather = weatherModel, error = null) }
                }.onFailure { ex ->
                    _uiState.update { it?.copy( error = getErrorMessage(ex)) }
                }
            }
        }*/


}
