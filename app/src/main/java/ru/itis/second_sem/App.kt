package ru.itis.second_sem

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp
import ru.itis.second_sem.utils.ActivityLifecycleHandler
import javax.inject.Inject

@HiltAndroidApp
class App: Application() {

    @Inject
    lateinit var activityLifecycleHandler: ActivityLifecycleHandler

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(activityLifecycleHandler)
    }

}