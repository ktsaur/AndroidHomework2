package ru.itis.second_sem.utils

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import ru.itis.second_sem.App
import ru.itis.second_sem.di.components.AppComponent
import ru.itis.second_sem.presentation.utils.AssistedFactory

fun Context.appComponent(): AppComponent {
    return when (this) {
        is App -> appComponent
        else -> (this.applicationContext as App).appComponent
    }
}

inline fun <reified T : ViewModel> Fragment.lazyViewModel(noinline create: (SavedStateHandle) -> T) =
    viewModels<T> {
        AssistedFactory(this, create)
    }