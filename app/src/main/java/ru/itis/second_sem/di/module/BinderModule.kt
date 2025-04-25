package ru.itis.second_sem.di.module

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.itis.second_sem.data.repository.WeatherRepositoryImpl
import ru.itis.second_sem.domain.repository.WeatherRepository
import ru.itis.second_sem.presentation.utils.MultibindingFactorySample
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface BinderModule { //модуль, чтобы связывать классы и их имплементации
    @Binds
    @Singleton
    fun bindWeatherRepositoryToImpl(impl: WeatherRepositoryImpl): WeatherRepository

    @Binds
    fun bindMultibindingFactoryToImpl(impl: MultibindingFactorySample):ViewModelProvider.Factory
}