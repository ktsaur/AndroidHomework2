package ru.itis.second_sem.presentation.screens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.itis.second_sem.di.ServiceLocator
import ru.itis.second_sem.domain.model.WeatherModel
import ru.itis.second_sem.domain.usecase.GetWeatherByCityNameUseCase
import javax.inject.Inject

class CurrentTempViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetWeatherByCityNameUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _currentWeatherFlow = MutableStateFlow<WeatherModel?>(null)
    //это поток данных
    // в модельке. когда мы кладем туда какое-то значение,
    // он уведомляет всех, кто на него подписан

    // state flow - горячий источник данных. это означает, что он будет порождать свои данные,
    // независимо от того, если ли у него обсерверы (подписчики)

    // холодные источники хранят данные до тех пор, пока не появится первый подписчик.
    // порождение данных начинается в тот момент, когда появится первый подписчик
    val currentWeatherFlow = _currentWeatherFlow.asStateFlow()//на этот флоу подписывается фрагмент

    //мы делаем один приватный MutableStateFlow и публичный currentWeatherFlow на него подписывается.
    // это делается для того, чтобы исключить возможность изменения данных флоу откуда-то извне.
    // вьюшка только подписывается на изменение данных и следит за этим. как только данные изменяются, она их показывает

    fun getCurrentWeather(city: String) {
        viewModelScope.launch {
            runCatching {
                getCurrentWeatherUseCase.invoke(city = city)
            }.onSuccess { weatherModel ->
                _currentWeatherFlow.value = weatherModel
            } .onFailure { ex ->
                println("TEST - Exception occurred: $ex")
            }
        }
    }

/*    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Inject
            lateinit var getCurrentWeatherUseCase: GetWeatherByCityNameUseCase

            private val getWeatherByCityNameUseCase by lazy { ServiceLocator.getWeatherUsecase() }

            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CurrentTempViewModel(getCurrentWeatherUseCase = getWeatherByCityNameUseCase) as T
            }
        }
    }*/
}