package ru.itis.second_sem.presentation.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.itis.second_sem.data.database.model.ForecastDataModel
import ru.itis.second_sem.data.database.model.WeatherDataModel
import ru.itis.second_sem.domain.model.ForecastModel
import ru.itis.second_sem.domain.model.WeatherModel
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

inline fun <T> Flow<T>.observe(
    lifecycleOwner: LifecycleOwner,
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: (T) -> Unit
): Job {
    /*
    lifecycleOwner - код не запускается, если вьюшка уничтожена
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

fun WeatherDataModel.toDomain(): WeatherModel {
    return WeatherModel(
        currentTemp = currentTemp, weatherDescription = description
    )
}

fun WeatherModel.toData(): WeatherDataModel {
    return WeatherDataModel(
        currentTemp = currentTemp, description = weatherDescription
    )
}

fun ForecastDataModel.toDomain(): ForecastModel {
    return ForecastModel(
        dt = dt,
        temp = temp,
        feelsLike = feelsLike,
        tempMin = tempMin,
        tempMax = tempMax,
        mainDesc = mainDesc,
        description = description
    )
}

fun List<ForecastDataModel>.toDomain(): List<ForecastModel> {
    return this.map { ForecastModel(
        dt = it.dt,
        temp = it.temp,
        feelsLike = it.feelsLike,
        tempMin = it.tempMin,
        tempMax = it.tempMax,
        mainDesc = it.mainDesc,
        description = it.description
    ) }
}

fun ForecastModel.toData(): ForecastDataModel {
    return ForecastDataModel(
        dt = dt,
        temp = temp,
        feelsLike = feelsLike,
        tempMin = tempMin,
        tempMax = tempMax,
        mainDesc = mainDesc,
        description = description
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun convertTimestampToTime(dt: Long?): String {
    val instant = dt?.let { Instant.ofEpochSecond(it) }
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
        .withZone(ZoneId.systemDefault())
    return formatter.format(instant)
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavController, navGraphRoute: String, navBackStackEntry: NavBackStackEntry
): T {
    val parentEntry  = remember(navBackStackEntry){
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}
