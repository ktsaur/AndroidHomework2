package ru.itis.second_sem.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import ru.itis.second_sem.data.remote.pojo.CurrentWeatherResponse
import ru.itis.second_sem.data.remote.pojo.ForecastWeatherResponse
import ru.itis.second_sem.domain.model.ForecastModel
import ru.itis.second_sem.presentation.utils.convertTimestampToTime
import javax.inject.Inject

class ForecastResponseMapper @Inject constructor(){
    @RequiresApi(Build.VERSION_CODES.O)
    fun map(input: ForecastWeatherResponse?): List<ForecastModel> {
        return input?.weaterList?.map { weather ->
                ForecastModel (
                    dt = convertTimestampToTime(weather.dt),
                    temp = weather.mainData?.temp ?: 0F,
                    feelsLike = weather.mainData?.feelsLike ?: 0F,
                    tempMin = weather.mainData?.tempMin ?: 0F,
                    tempMax = weather.mainData?.tempMax ?: 0F,
                    mainDesc = weather.weatherCondition?.firstOrNull()?.mainDesc,
                    description = weather.weatherCondition?.firstOrNull()?.description
                )
        } ?: emptyList()
    }
}