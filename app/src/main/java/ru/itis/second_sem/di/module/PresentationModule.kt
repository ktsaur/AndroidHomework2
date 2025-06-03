package ru.itis.second_sem.di.module


import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import dagger.multibindings.Multibinds
import ru.itis.auth.utils.AuthManager
import ru.itis.second_sem.presentation.navigation.NavigationManager
import ru.itis.second_sem.utils.ActivityLifecycleHandler
import ru.itis.second_sem.utils.NotificationHandler

@Module
@InstallIn(SingletonComponent::class)

class PresentationModule {
    @Provides
    fun provideNotificationHandler(
        @ApplicationContext context: Context,
        authManager: AuthManager,
        activityLifecycleHandler: ActivityLifecycleHandler,
        navigationManager: NavigationManager
    ): NotificationHandler =
        NotificationHandler(context, authManager, activityLifecycleHandler, navigationManager)

    @Provides
    fun provideAuthManager(@ApplicationContext context: Context): AuthManager = AuthManager(context)

    @Provides
    fun provideNavigationManager(): NavigationManager = NavigationManager()

}