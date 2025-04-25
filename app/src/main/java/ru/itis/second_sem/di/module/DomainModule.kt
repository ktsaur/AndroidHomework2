package ru.itis.second_sem.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import ru.itis.second_sem.di.qualifiers.DefaultDispatchers
import ru.itis.second_sem.di.qualifiers.IoDispatchers

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    @IoDispatchers
    fun provideIoDispatcher(): CoroutineDispatcher{
        return Dispatchers.IO
    }

    @Provides
    @DefaultDispatchers
    fun provideDefaultDispatcher(): CoroutineDispatcher{
        return Dispatchers.Default
    }
}