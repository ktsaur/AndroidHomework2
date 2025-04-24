package ru.itis.second_sem.presentation.screens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import ru.itis.second_sem.domain.usecase.GetWeatherByCityNameUseCase

class TempDetailsViewModel @AssistedInject constructor ( //говорит, что зависимости этого класса будут резолвиться как обычно
    private val getWeatherByCityNameUsecase: GetWeatherByCityNameUseCase,
    @Assisted("longitude") private val longitude: Float, //долгота. аннотация говорит о том, что эти щависимости мы предоставим сами
    @Assisted("latitude") private val latitude: Float, //широта
    @Assisted private val savedStateHundle: SavedStateHandle
) : ViewModel() {

    fun start() {}

    @AssistedFactory
    interface Factory{
        fun create(
            @Assisted("longitude") longitude: Float,
            @Assisted("latitude") latitude: Float,
            savedStateHundle: SavedStateHandle
        ) : TempDetailsViewModel //метод, который получает все параметры, которые должны быть переданы через @Assisted
    }

}