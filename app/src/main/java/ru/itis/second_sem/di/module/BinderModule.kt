package ru.itis.second_sem.di.module

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import ru.itis.second_sem.data.repository.WeatherRepositoryImpl
import ru.itis.second_sem.domain.repository.WeatherRepository
import ru.itis.second_sem.presentation.utils.MultibindingFactorySample
import javax.inject.Singleton

@Module
interface BinderModule { //модуль, чтобы связывать классы и их имплементации
    @Binds
    @Singleton
    fun bindWeatherRepositoryToImpl(impl: WeatherRepositoryImpl): WeatherRepository

    @Binds
    fun bindMultibindingFactoryToImpl(impl: MultibindingFactorySample):ViewModelProvider.Factory
}