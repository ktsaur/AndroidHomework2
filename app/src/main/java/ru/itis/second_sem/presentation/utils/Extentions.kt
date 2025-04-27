package ru.itis.second_sem.presentation.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

inline fun <T> Flow<T>.observe(
    lifecycleOwner: LifecycleOwner,
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: (T) -> Unit
): Job {
    /*
    lifecycleOwner - код не запускается, если вьюшка уничтожена.
    если приложение на он стоп и он пауз, то данные не коллектятся
    lifecycleState - состояние жизненного цикла компонента
    */
    return lifecycleOwner.lifecycleScope.launch { // запускается корутина, привязанная к жизненному циклу
        lifecycleOwner.repeatOnLifecycle(lifecycleState) { // repeatOnLifecycle — повторяем collect каждый раз, когда lifecycleOwner в нужном состоянии
            collect{
                block.invoke(it)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun convertTimestampToTime(dt: Long?): String {
    val instant = dt?.let { Instant.ofEpochSecond(it) }
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
        .withZone(ZoneId.systemDefault())
    return formatter.format(instant)
}
