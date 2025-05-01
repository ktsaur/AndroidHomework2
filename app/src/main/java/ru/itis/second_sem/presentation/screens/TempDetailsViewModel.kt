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
import ru.itis.second_sem.domain.usecase.GetForecastByCityNameUseCase
import ru.itis.second_sem.domain.usecase.GetWeatherByCityNameUseCase
import ru.itis.second_sem.presentation.uiState.WeatherUIState
import javax.inject.Inject

@HiltViewModel
class TempDetailsViewModel @Inject constructor (
    private val getForecastByCityNameUseCase: GetForecastByCityNameUseCase,
    private val getWeatherByCityNameUseCase: GetWeatherByCityNameUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<WeatherUIState?>(WeatherUIState())
    val uiState = _uiState.asStateFlow()

    fun getForecast(city: String) {
        _uiState.update { it?.copy(error = null) }
        viewModelScope.launch {
            runCatching {
                getForecastByCityNameUseCase.invoke(city = city)
            }.onSuccess { list ->
                _uiState.update { it?.copy(forecast = list, error = null) }
            }.onFailure { ex ->
                Log.d("TEST_TAG", "Exception: ${ex}")
                _uiState.update { it?.copy(error = getErrorMessage(ex)) }
            }
        }
    }

    fun getCurrentTemp(city: String) {
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
    }

    private fun getErrorMessage(ex: Throwable): String? {
        return when(ex) {
            is HttpException -> {
                when (ex.code()) {
                    401 -> "401 статусный код. Для доступа требуется аутентификация."
                    404 -> "404 статусный код. Город не найден."
                    else -> {"Ошибка сервера: ${ex.code()}"}
                }
            }
            is IOException -> "Ошибка соединения с интернетом."
            else -> {"Неизвестная ошибка. Попробуйте позже."}
        }
    }
}