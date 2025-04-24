package ru.itis.second_sem.presentation.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import javax.inject.Inject
import javax.inject.Provider //делает отложенное предоставление зависимости
import javax.inject.Singleton


@Suppress("UNCHECKED_CAST")
class MultibindingFactorySample @Inject constructor(
    private val viewModelMap: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        val result = viewModelMap[modelClass] ?: viewModelMap.entries.firstOrNull { entry ->
            modelClass.isAssignableFrom(entry.key)
        }?.value ?: throw IllegalStateException("VM class not found: ${modelClass.canonicalName}")

        return result.get() as T
    }
}