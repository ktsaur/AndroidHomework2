package ru.itis.second_sem.presentation.screens.tempDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.itis.auth.R
import ru.itis.second_sem.data.database.InceptionDatabase
import ru.itis.second_sem.data.database.entity.QueryHistoryEntity
import ru.itis.second_sem.data.database.entity.WeatherApiEntity
import ru.itis.second_sem.domain.usecase.GetForecastByCityNameUseCase
import ru.itis.second_sem.domain.usecase.GetWeatherByCityNameUseCase
import ru.itis.second_sem.presentation.utils.CityValidationException
import ru.itis.second_sem.presentation.utils.toData
import ru.itis.second_sem.presentation.utils.toDomain
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class TempDetailsViewModel @Inject constructor(
    private val getForecastByCityNameUseCase: GetForecastByCityNameUseCase,
    private val getWeatherByCityNameUseCase: GetWeatherByCityNameUseCase,
    private val database: InceptionDatabase
) : ViewModel() {
    private val _uiState = MutableStateFlow(WeatherUIState())
    val uiState = _uiState.asStateFlow()

    private val _effectFlow = MutableSharedFlow<TempDetailsEffect>()
    val effectFlow = _effectFlow.asSharedFlow()

    fun onEvent(event: TempDetailsEvent) {
        when(event) {
            is TempDetailsEvent.OnErrorConfirm -> {
                viewModelScope.launch {
                    _effectFlow.emit(TempDetailsEffect.NavigateBack)
                }
            }
            is TempDetailsEvent.CityUpdate -> { _uiState.update { it.copy(city = event.city, error = null) } }
            is TempDetailsEvent.GetWeatherBtnClicked -> {
                getForecast(city = _uiState.value.city)
                viewModelScope.launch {
                    _effectFlow.emit(TempDetailsEffect.NavigateToTempDetails(city = _uiState.value.city))
                }
            }
        }
    }

    fun getForecast(city: String) {
        if (city.firstOrNull()?.isLowerCase() == true) {
            _uiState.update { it.copy(error = CityValidationException(),  city = city, isLoading = false) }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(error = null,  city = city, isLoading = true) }

            val lastTimestamp = withContext(Dispatchers.IO) {
                database.queryHistoryDao.getLastQueryForCity(city = city)
            }

            val shouldFetchFromApi = withContext(Dispatchers.IO) {
                if (lastTimestamp == null) {
                    return@withContext true
                }
                val countBetween =
                    database.queryHistoryDao.countQueryBetween(
                        start = lastTimestamp,
                        end = System.currentTimeMillis()
                    )
                return@withContext (abs(lastTimestamp - System.currentTimeMillis()) >= 5 * 60 * 1000 || countBetween >= 3)
            }

            if (shouldFetchFromApi) {
                fetchFromApi(city = city)
                _effectFlow.emit(TempDetailsEffect.ShowToast(message = R.string.fetch_from_api))
            } else {
                fetchFromDb(city = city)
                _effectFlow.emit(TempDetailsEffect.ShowToast(message = R.string.fetch_from_db))
            }
        }
    }

    private suspend fun insertQueryHistory(query: QueryHistoryEntity) {
        withContext(Dispatchers.IO) { database.queryHistoryDao.insertQuery(queryHistoryEntity = query) }
    }

    private suspend fun fetchFromApi(city: String) {
        runCatching {
            val forecast = getForecastByCityNameUseCase.invoke(city = city)
            val weather = getWeatherByCityNameUseCase.invoke(city = city)
            Pair(forecast, weather)
        }.onSuccess { (forecast, weather) ->
            _uiState.update {
                it.copy(
                    forecast = forecast,
                    weather = weather,
                    error = null,
                    isLoading = false
                )
            }
            withContext(Dispatchers.IO) {
                val weatherApiEntity =
                    WeatherApiEntity(
                        city = city,
                        currentTemp = weather.toData(),
                        forecast = forecast.map { it.toData() },
                    )
                database.weatherApiDao.insertWeatherApi(weatherApiEntity)
            }
            insertQueryHistory(
                QueryHistoryEntity(
                    city = city,
                    timestamp = System.currentTimeMillis()
                )
            )
        }.onFailure { ex ->
            _uiState.update { it.copy(error = ex, isLoading = false) }
        }
    }

    private suspend fun fetchFromDb(city: String) {
        withContext(Dispatchers.IO) {
            database.weatherApiDao.getWeatherApi(city)
        }?.let { weatherApiEntity ->
            _uiState.update {
                it.copy(
                    forecast = weatherApiEntity.forecast.toDomain(),
                    weather = weatherApiEntity.currentTemp.toDomain(),
                    error = null,
                    isLoading = false
                )
            }
            insertQueryHistory(
                QueryHistoryEntity(
                    city = city,
                    timestamp = System.currentTimeMillis()
                )
            )
        }
    }

}
