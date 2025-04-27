package ru.itis.second_sem.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import ru.itis.second_sem.data.remote.pojo.CurrentWeatherResponse
import ru.itis.second_sem.data.remote.pojo.ForecastWeatherResponse

interface OpenWeatherApi {
    @GET("weather")
    suspend fun getCurrentWeatherByCityName(
        @Query("q") city: String
    ): CurrentWeatherResponse?

    @GET("forecast")
    suspend fun getForecastWeatherByCityName(
        @Query("q") city: String
    ): ForecastWeatherResponse?
}