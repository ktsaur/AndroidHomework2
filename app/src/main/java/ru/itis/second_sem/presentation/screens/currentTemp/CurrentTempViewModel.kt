package ru.itis.second_sem.presentation.screens.currentTemp

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentTempViewModel @Inject constructor(): ViewModel() {

    private val _uiState = MutableStateFlow(CurrentTempUiState())
    val uiState = _uiState.asStateFlow()

    private val _effectFlow = MutableSharedFlow<CurrentTempEffect>()
    val effectFlow = _effectFlow.asSharedFlow()

    fun onEvent(event: CurrentTempEvent) {
        when (event) {
            is CurrentTempEvent.CityUpdate -> {
                _uiState.update { it.copy(city = event.city) }
            }
            is CurrentTempEvent.GetWeatherBtnClicked -> {
                viewModelScope.launch {
                    Log.d("TempViewModel", "Navigating to details with city: ${_uiState.value.city}")
                    _effectFlow.emit(CurrentTempEffect.NavigateToTempDetails(city = _uiState.value.city))
                }
            }
        }
    }
}