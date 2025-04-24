package ru.itis.second_sem.di.components

import dagger.Component
import ru.itis.second_sem.di.module.BinderModule
import ru.itis.second_sem.di.module.DataModule
import ru.itis.second_sem.di.module.DomainModule
import ru.itis.second_sem.di.module.PresentationModule
import ru.itis.second_sem.presentation.base.MainActivity
import ru.itis.second_sem.presentation.screens.CurrentTempFragment
import ru.itis.second_sem.presentation.screens.TempDetailsViewModel
import javax.inject.Singleton

@Component(modules = [DataModule::class, DomainModule::class, BinderModule::class, PresentationModule::class])
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {
        fun build(): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: CurrentTempFragment)

    fun tempDetailsViewModel(): TempDetailsViewModel.Factory
}