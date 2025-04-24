package ru.itis.second_sem.di.module

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
class DomainModule {

    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher{
        return Dispatchers.IO
    }
}