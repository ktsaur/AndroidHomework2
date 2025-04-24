package ru.itis.second_sem.data.remote.pojo

import com.google.gson.annotations.SerializedName

class CurrentWeatherResponse (
    @SerializedName("main")
    val mainData: MainData? = null,
    @SerializedName("wind")
    val wind: Wind?= null
)

class MainData(
    @SerializedName("temp")
    val temp: Float?,
    @SerializedName("feels_like")
    val feelsLike: Float?,
    @SerializedName("temp_min")
    val minTemp: Float?,
    @SerializedName("temp_max")
    val maxTemp: Float?,
    @SerializedName("pressure")
    val pressure:Float?
)

class Wind(
    @SerializedName("speed")
    val speed: Float?
)