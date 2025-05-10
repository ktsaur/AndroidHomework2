package ru.itis.second_sem.data.database.converters

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.itis.second_sem.data.database.model.ForecastDataModel

class ForecastConverter {
    private val json = Json {}

    @TypeConverter
    fun fromForecastList(value: List<ForecastDataModel>): String {
        return json.encodeToString(value)
    }

    @TypeConverter
    fun toForecastList(value: String): List<ForecastDataModel> {
        return json.decodeFromString(value)
    }
}