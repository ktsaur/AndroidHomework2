package ru.itis.second_sem

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import ru.itis.second_sem.utils.NotificationHandler
import javax.inject.Inject

@AndroidEntryPoint
//этот сервис принимаеи пуши от фаербейс
class DemoAppMessaging (
) : FirebaseMessagingService() {

    @Inject lateinit var notificationHandler: NotificationHandler

    //этот метод вызывается в тот момент, когда наше устройство
    // получает пуш-токен, по которому присылаются пуши конкретно нашему устройству
    override fun onNewToken(token: String) {
        super.onNewToken(token)

    }

    // вызывается в тот момент когда приходит новый пуш и надо его обработать
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        notificationHandler.createNotification(context = this, id = 122, message = message)
    }
}