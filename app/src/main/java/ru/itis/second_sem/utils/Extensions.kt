package ru.itis.second_sem.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import ru.itis.second_sem.presentation.utils.AssistedFactory


inline fun <reified T : ViewModel> Fragment.lazyViewModel(noinline create: (SavedStateHandle) -> T) =
    viewModels<T> {
        AssistedFactory(this, create)
    }