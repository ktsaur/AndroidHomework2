package ru.itis.second_sem.utils

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ActivityLifecycleHandler @Inject constructor() : ActivityLifecycleCallbacks {
    private var startStatus = 0

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
    override fun onActivityStarted(activity: Activity) { ++startStatus }
    override fun onActivityResumed(activity: Activity) {}
    override fun onActivityPaused(activity: Activity) {}
    override fun onActivityStopped(activity: Activity) { --startStatus }
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
    override fun onActivityDestroyed(activity: Activity) {}
    fun isAppInForeground(): Boolean { return startStatus > 0 }

}