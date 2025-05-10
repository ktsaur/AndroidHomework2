package ru.itis.second_sem.presentation.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import ru.itis.second_sem.domain.usecase.GetWeatherByCityNameUseCase
import javax.inject.Inject
import kotlin.reflect.KClass

/*
class LegacyViewModelFactory @Inject constructor(
    private val getWeatherByCityNameUsecase: GetWeatherByCityNameUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return when {
            modelClass.isAssignableFrom(CurrentTempViewModel::class.java) -> {
                val savedStateHandle = extras.createSavedStateHandle() //чтобы сохранять какие-то значения и восстанавливать из в случае уничтожения процесса
                CurrentTempViewModel(getCurrentWeatherUseCase = getWeatherByCityNameUsecase,
                    savedStateHandle = savedStateHandle) as T
            }
            else -> {
                throw IllegalStateException("VM class nor found: ${modelClass.canonicalName}")
            }
        }
    }
}*/
