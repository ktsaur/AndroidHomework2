package ru.itis.second_sem.data.mapper

import ru.itis.second_sem.data.database.model.ForecastDataModel
import ru.itis.second_sem.data.database.model.WeatherDataModel
import ru.itis.second_sem.domain.model.ForecastModel
import ru.itis.second_sem.domain.model.WeatherModel
import javax.inject.Inject

fun WeatherDataModel.toDomain(): WeatherModel {
    return WeatherModel(
        currentTemp = currentTemp, weatherDescription = description
    )
}

fun WeatherModel.toData(): WeatherDataModel {
    return WeatherDataModel(
        currentTemp = currentTemp, description = weatherDescription
    )
}

fun ForecastDataModel.toDomain(): ForecastModel {
    return ForecastModel(
        dt = dt,
        temp = temp,
        feelsLike = feelsLike,
        tempMin = tempMin,
        tempMax = tempMax,
        mainDesc = mainDesc,
        description = description
    )
}

fun List<ForecastDataModel>.toDomain(): List<ForecastModel> {
    return this.map { ForecastModel(
        dt = it.dt,
        temp = it.temp,
        feelsLike = it.feelsLike,
        tempMin = it.tempMin,
        tempMax = it.tempMax,
        mainDesc = it.mainDesc,
        description = it.description
    ) }
}

fun ForecastModel.toData(): ForecastDataModel {
    return ForecastDataModel(
        dt = dt,
        temp = temp,
        feelsLike = feelsLike,
        tempMin = tempMin,
        tempMax = tempMax,
        mainDesc = mainDesc,
        description = description
    )
}
