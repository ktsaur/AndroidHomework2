package ru.itis.second_sem.presentation.screens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.itis.second_sem.domain.usecase.GetWeatherByCityNameUseCase
import javax.inject.Inject

@HiltViewModel
class TempDetailsViewModel @Inject constructor ( //говорит, что зависимости этого класса будут резолвиться как обычно
    private val getWeatherByCityNameUsecase: GetWeatherByCityNameUseCase,
/*    @Assisted("longitude") private val longitude: Float, //долгота. аннотация говорит о том, что эти щависимости мы предоставим сами
    @Assisted("latitude") private val latitude: Float, //широта*/
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val longitude: Float = savedStateHandle["longitude"] ?: 0f
    private val latitude: Float = savedStateHandle["latitude"] ?: 0f

    fun start() {}

  /*  @AssistedFactory
    interface Factory{
        fun create(
            @Assisted("longitude") longitude: Float,
            @Assisted("latitude") latitude: Float,
            savedStateHundle: SavedStateHandle
        ) : TempDetailsViewModel //метод, который получает все параметры, которые должны быть переданы через @Assisted
    }*/

}