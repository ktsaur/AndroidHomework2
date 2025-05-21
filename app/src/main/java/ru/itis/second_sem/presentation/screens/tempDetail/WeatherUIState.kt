package ru.itis.second_sem.presentation.screens.tempDetail

import androidx.compose.runtime.Immutable
import ru.itis.auth.presentation.registration.RegistrationEffect
import ru.itis.second_sem.domain.model.ForecastModel
import ru.itis.second_sem.domain.model.WeatherModel
import ru.itis.second_sem.presentation.screens.currentTemp.CurrentTempEffect
import ru.itis.second_sem.presentation.screens.currentTemp.CurrentTempEvent

@Immutable
data class WeatherUIState (
    val city: String = "",
    val weather: WeatherModel = WeatherModel.EMPTY,
    val forecast: List<ForecastModel>? = emptyList(),
    val error: Throwable? = null,
    val isLoading: Boolean = true
)

sealed class TempDetailsEffect {
    data object NavigateBack: TempDetailsEffect()
    data class NavigateToTempDetails(val city: String) : TempDetailsEffect()
    data class ShowToast(val message: Int): TempDetailsEffect()
    data object NavigateToGraph: TempDetailsEffect()
}

sealed class TempDetailsEvent {
    data object OnErrorConfirm: TempDetailsEvent()
    data object GetWeatherBtnClicked: TempDetailsEvent()
    data class CityUpdate(val city: String): TempDetailsEvent()
    data object NavigateToGraph: TempDetailsEvent()
}

