package ru.itis.second_sem.presentation.UIState

import ru.itis.second_sem.domain.model.ForecastModel
import ru.itis.second_sem.domain.model.WeatherModel

data class WeatherUIState (
    val weather: WeatherModel? = null,
    val forecast: List<ForecastModel>? = null,
    val loading: Boolean = false,
)