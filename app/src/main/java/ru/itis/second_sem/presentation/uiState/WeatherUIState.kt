package ru.itis.second_sem.presentation.uiState

import ru.itis.second_sem.domain.model.ForecastModel
import ru.itis.second_sem.domain.model.WeatherModel

data class WeatherUIState (
    val weather: WeatherModel = WeatherModel.EMPTY,
    val forecast: List<ForecastModel>? = emptyList(),
    val error: String? = null,
)