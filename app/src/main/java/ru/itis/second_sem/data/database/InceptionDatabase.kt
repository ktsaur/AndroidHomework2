package ru.itis.second_sem.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import ru.itis.second_sem.data.database.converters.CurrentTempConverter
import ru.itis.second_sem.data.database.converters.ForecastConverter
import ru.itis.second_sem.data.database.dao.WeatherApiDao
import ru.itis.second_sem.data.database.entity.WeatherApiEntity
import javax.inject.Inject

@Database(
    entities = [WeatherApiEntity::class],
    version = 2
)
@TypeConverters(CurrentTempConverter::class, ForecastConverter::class)
abstract class InceptionDatabase: RoomDatabase() {
    abstract val weatherApiDao: WeatherApiDao
}