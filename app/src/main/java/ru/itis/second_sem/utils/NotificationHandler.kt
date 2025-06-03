package ru.itis.second_sem.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.text.toLowerCase
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.itis.auth.utils.AuthManager
import ru.itis.second_sem.R
import ru.itis.second_sem.presentation.base.MainActivity
import ru.itis.second_sem.presentation.navigation.NavigationManager
import ru.itis.second_sem.presentation.navigation.Screen
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationHandler @Inject constructor(
    @ApplicationContext private val applicationContext: Context,
    private val authManager: AuthManager,
    private val activityLifecycleHandler: ActivityLifecycleHandler,
    private val navigationManager: NavigationManager
) {
    companion object {
        private const val CHANNEL_ID = "notification_channel"
        private const val CHANNEL_NAME = "name_notification"
        private const val PREFS_NAME = "notification_pref"
    }

    private val sharedPreferences: SharedPreferences = applicationContext.getSharedPreferences(
        PREFS_NAME, Context.MODE_PRIVATE
    )

    fun createNotification(context: Context, id: Int, message: RemoteMessage) {
        val title = message.data["title"] ?: applicationContext.getString(R.string.default_title)
        val text = message.data["text"] ?: applicationContext.getString(R.string.default_message)
        val category = message.data["category"] ?: applicationContext.getString(R.string.default_category)
        /* val title = message.notification?.title ?: ""
         val text = message.notification?.body ?: ""
         val category = message.notification*/

        when (category.lowercase()) {
            "first" -> createAlertNotification(context, id, title, text)
            "second" -> createPrefsNotification(title, text)
            "third" -> handleFeatureCategory()
            else -> createAlertNotification(context, id, title, text)
        }
    }

    private fun isAuthorized(): Boolean {
        return (authManager.getUserId() != -1)
    }

    private fun createAlertNotification(context: Context, id: Int, title: String, text: String) {
        (context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager)?.let { manager ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH
                )
                manager.createNotificationChannel(channel)
            }

            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(
                context,
                101,
                intent,
                PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
            )

            val notification =
                NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.icon_weather)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setAutoCancel(true)
                    .setCategory(NotificationCompat.CATEGORY_ALARM)
                    .setContentIntent(pendingIntent)

            manager.notify(id, notification.build())
        }
    }

    private fun createPrefsNotification(title: String, text: String) {
        val data = "title = $title, text = $text"
        sharedPreferences.edit().putString(PREFS_NAME, data).apply()
    }

    private fun isScreenActive(): Boolean {
        return activityLifecycleHandler.isAppInForeground()
    }

    private fun handleFeatureCategory() {
        CoroutineScope(Dispatchers.Main).launch {
            if (!isAuthorized()) {
                Toast.makeText(
                    applicationContext,
                    applicationContext.getString(R.string.access_to_feature_closed),
                    Toast.LENGTH_SHORT
                ).show()
                return@launch
            }
            if (!isScreenActive()) {
                return@launch
            }
            if (navigationManager.getCurrentRoute() == Screen.Graph.route) {
                Toast.makeText(
                    applicationContext,
                    applicationContext.getString(R.string.you_are_on_the_feature_screen),
                    Toast.LENGTH_SHORT
                ).show()
                return@launch
            }
            navigationManager.navigate(Screen.Graph.route)
        }
    }
}
