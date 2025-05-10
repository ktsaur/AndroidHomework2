package ru.itis.second_sem.presentation.screens.tempDetail

import ru.itis.auth.presentation.registration.RegistrationEffect
import ru.itis.second_sem.domain.model.ForecastModel
import ru.itis.second_sem.domain.model.WeatherModel

data class WeatherUIState (
    val city: String = "",
    val weather: WeatherModel = WeatherModel.EMPTY,
    val forecast: List<ForecastModel>? = emptyList(),
    val error: Throwable? = null,
    val isLoading: Boolean = true
)

sealed class TempDetailsEffect() {
    data object NavigateBack: TempDetailsEffect()
    data class ShowToast(val message: String): TempDetailsEffect()
}

sealed class TempDetailsEvent() {
    data object OnErrorConfirm: TempDetailsEvent()
}

