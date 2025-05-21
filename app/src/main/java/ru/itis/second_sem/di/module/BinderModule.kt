package ru.itis.second_sem.di.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.itis.auth.domain.repository.UserRepository
import ru.itis.second_sem.data.database.repository.UserRepositoryImpl
import ru.itis.second_sem.data.repository.ForecastRepositoryImpl
import ru.itis.second_sem.data.repository.WeatherRepositoryImpl
import ru.itis.second_sem.domain.repository.ForecastRepository
import ru.itis.second_sem.domain.repository.WeatherRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface BinderModule { //модуль, чтобы связывать классы и их имплементации
    @Binds
    @Singleton
    fun bindWeatherRepositoryToImpl(impl: WeatherRepositoryImpl): WeatherRepository

    @Binds
    @Singleton
    fun bindForecastRepositoryToImpl(impl: ForecastRepositoryImpl): ForecastRepository

}