package ru.itis.second_sem.di.module


import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import dagger.multibindings.Multibinds
import ru.itis.second_sem.di.keys.ViewModelKey
import ru.itis.second_sem.presentation.screens.CurrentTempViewModel

@Module
@InstallIn(SingletonComponent::class)
class PresentationModule {

    @Provides
    @IntoMap
    //в качестве ключа мапы используется название класса вью модели, потому что по названию класса вью модельки хранаться в модел сторе
    @ViewModelKey(CurrentTempViewModel::class)
    fun bindCurrentTempViewModel(
        vm:CurrentTempViewModel
    ) : ViewModel = vm

}