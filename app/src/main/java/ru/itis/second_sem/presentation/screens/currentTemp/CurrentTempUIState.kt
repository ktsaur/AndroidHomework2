package ru.itis.second_sem.presentation.screens.currentTemp

data class CurrentTempUiState(
    val city: String = "",
)

sealed class CurrentTempEvent() {
    data object GetWeatherBtnClicked: CurrentTempEvent()
    data class CityUpdate(val city: String): CurrentTempEvent()
}

sealed interface CurrentTempEffect {
    data class NavigateToTempDetails(val city: String) : CurrentTempEffect
}
