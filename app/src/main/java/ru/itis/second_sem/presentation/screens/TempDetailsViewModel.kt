package ru.itis.second_sem.presentation.screens

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.itis.second_sem.domain.model.ForecastModel
import ru.itis.second_sem.domain.usecase.GetForecastByCityNameUseCase
import ru.itis.second_sem.domain.usecase.GetWeatherByCityNameUseCase
import ru.itis.second_sem.presentation.UIState.WeatherUIState
import javax.inject.Inject

@HiltViewModel
class TempDetailsViewModel @Inject constructor (
    private val getForecastByCityNameUseCase: GetForecastByCityNameUseCase,
    private val getWeatherByCityNameUseCase: GetWeatherByCityNameUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<WeatherUIState?>(WeatherUIState())
    val uiState = _uiState.asStateFlow()

    fun getForecast(city: String) {
        _uiState.update { it?.copy(loading = true) }
        viewModelScope.launch {
            runCatching {
                getForecastByCityNameUseCase.invoke(city = city)
            }.onSuccess { list ->
                _uiState.update { it?.copy(forecast = list, loading = false) }
            }.onFailure { ex ->
                Log.d("TEST_TAG", "Exception: ${ex}")
                _uiState.update { it?.copy(loading = true) }
            }
        }
    }

    fun getCurrentTemp(city: String) {
        _uiState.update { it?.copy(loading = true) }
        viewModelScope.launch {
            runCatching {
                getWeatherByCityNameUseCase.invoke(city = city)
            }.onSuccess {  weatherModel ->
                _uiState.update { it?.copy(weather = weatherModel, loading = false) }
            }.onFailure { ex ->
                _uiState.update { it?.copy(loading = true) }
            }
        }
    }
}