package ru.itis.second_sem.presentation.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException
import ru.itis.second_sem.R
import ru.itis.second_sem.domain.usecase.GetForecastByCityNameUseCase
import ru.itis.second_sem.domain.usecase.GetWeatherByCityNameUseCase
import ru.itis.second_sem.presentation.uiState.WeatherUIState
import javax.inject.Inject

@HiltViewModel
class TempDetailsViewModel @Inject constructor (
    private val getForecastByCityNameUseCase: GetForecastByCityNameUseCase,
    private val getWeatherByCityNameUseCase: GetWeatherByCityNameUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUIState())
    val uiState = _uiState.asStateFlow()

    fun getForecast(city: String) {
        _uiState.update { it.copy(error = null) }
        viewModelScope.launch {
            runCatching {
                val forecast = getForecastByCityNameUseCase.invoke(city = city)
                val weather = getWeatherByCityNameUseCase.invoke(city = city)
                Pair(forecast, weather)
            }.onSuccess { (forecast, weather) ->
                _uiState.update { it.copy(forecast = forecast, weather = weather, error = null) }
            }.onFailure { ex ->
                setError(ex)
            }
        }
    }

    fun setError(ex: Throwable) {
        _uiState.update { it.copy(error = ex) }

    }

/*    fun getCurrentTemp(city: String) {
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