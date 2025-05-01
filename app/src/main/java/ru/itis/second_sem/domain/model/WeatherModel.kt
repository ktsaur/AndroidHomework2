package ru.itis.second_sem.domain.model

data class WeatherModel(
    val currentTemp: Float,
    val weatherDescription: String
) {
    companion object{
        val EMPTY = WeatherModel(
            currentTemp = 0.0f,
            weatherDescription = "",
        )
    }
}

