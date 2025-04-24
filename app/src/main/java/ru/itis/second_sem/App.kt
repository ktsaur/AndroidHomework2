package ru.itis.second_sem

import android.app.Application
import ru.itis.second_sem.di.components.AppComponent
import ru.itis.second_sem.di.components.DaggerAppComponent

class App: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().build()
    }
}