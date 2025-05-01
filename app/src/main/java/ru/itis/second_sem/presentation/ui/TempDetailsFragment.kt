package ru.itis.second_sem.presentation.ui

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getColor
import kotlinx.coroutines.delay
import ru.itis.second_sem.R
import ru.itis.second_sem.domain.model.ForecastModel
import ru.itis.second_sem.domain.model.WeatherModel
import ru.itis.second_sem.presentation.uiState.WeatherUIState


@Composable
fun TempDetailsFragment(city: String, weatherUIState: WeatherUIState?) {
    var isLoading by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(key1 = true) {
        delay(3000)
        isLoading = false
    }
    Scaffold(containerColor = colorResource(id = R.color.blue)) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ShimmerTempCard(
                isLoading = isLoading,
                contentAfterLoading = {
                    TempMainCard(
                        city = city,
                        weatherUIState = weatherUIState
                    )
                }
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 36.dp)
            ) {
                Text(
                    text = "Первые сутки",
                    style = TextStyle(
                        color = Color.DarkGray,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
                Card(
                    modifier = Modifier.padding(top = 30.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.elevatedCardElevation(6.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        if (weatherUIState?.forecast?.isNotEmpty() == true) {
                            weatherUIState.forecast.let {
                                itemsIndexed(
                                    it.subList(0, 8)
                                ) { _, item ->
                                    ShimmerListItem(
                                        isLoading = isLoading,
                                        contentAfterLoading = { ItemRow(forecastModel = item) })
                                }
                            }
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 36.dp)
            ) {
                Text(
                    text = "Вторые сутки",
                    style = TextStyle(
                        color = Color.DarkGray,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
                Card(
                    modifier = Modifier.padding(top = 30.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.elevatedCardElevation(6.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        if (weatherUIState?.forecast?.isNotEmpty() == true) {
                            weatherUIState.forecast.let {
                                itemsIndexed(
                                    it.subList(8, 16)
                                ) { _, item ->
                                    ShimmerListItem(
                                        isLoading = isLoading,
                                        contentAfterLoading = { ItemRow(forecastModel = item) })
                                }
                            }
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 36.dp)
            ) {
                Text(
                    text = "Третьи сутки",
                    style = TextStyle(
                        color = Color.DarkGray,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
                Card(
                    modifier = Modifier.padding(top = 30.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.elevatedCardElevation(6.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        if (weatherUIState?.forecast?.isNotEmpty() == true) {
                            weatherUIState.forecast.let {
                                itemsIndexed(
                                    it.subList(16, 24)
                                ) { _, item ->
                                    ShimmerListItem(
                                        isLoading = isLoading,
                                        contentAfterLoading = { ItemRow(forecastModel = item) })
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemRow(forecastModel: ForecastModel) {
    var openDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .size(58.dp)
            .clickable { openDialog = true },
        shape = RoundedCornerShape(10.dp),
        colors =  CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.blue)
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = forecastModel.dt, fontSize = 14.sp)
                Text(text = "${forecastModel.temp.toInt()}°C",fontSize = 18.sp, modifier = Modifier.padding(top = 3.dp))
            }
        }
    }

    if(openDialog) {
        AlertDialog(
            onDismissRequest = { openDialog = false },
            confirmButton = { 
                TextButton(onClick = { openDialog = false }) {
                    Text(text = "OK")
                }
            },
            title = { Text(text = "Detail temperature") },
            text = { Text(text = "time: ${forecastModel.dt} \n" +
                    "temp: ${forecastModel.temp.toInt()} \n" +
                    "feels like: ${forecastModel.feelsLike.toInt()} \n" +
                    "max temp: ${forecastModel.tempMax.toInt()} \n" +
                    "min temp: ${forecastModel.tempMin.toInt()} \n" +
                    "description: ${forecastModel.mainDesc} \n"
            ) }
        )
    }
}

@Composable
fun ShimmerListItem(
    isLoading: Boolean,
    contentAfterLoading: @Composable () -> Unit
) {
    if(isLoading) {
        Card(
            modifier = Modifier
                .size(58.dp)
                .shimmerEffect(),
            shape = RoundedCornerShape(10.dp),
            colors =  CardDefaults.cardColors(
                containerColor = colorResource(id = R.color.blue)
            )
        ) { }
    } else {
        contentAfterLoading()
    }
}

@Composable
fun ShimmerTempCard(
    isLoading: Boolean,
    contentAfterLoading: @Composable () -> Unit
) {
    if(isLoading) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .shimmerEffect(),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.elevatedCardElevation(6.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color.LightGray),
        ) { }
    } else {
        contentAfterLoading()
    }
}

@Composable
fun TempMainCard(city: String, weatherUIState: WeatherUIState?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.elevatedCardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color.LightGray),
    ) { // карточка с прогнозом на сейчас
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 68.dp, bottom = 40.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Temp in $city",
                    style = TextStyle(
                        color = Color.DarkGray,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
                Text(
                    text = "${weatherUIState?.weather?.currentTemp?.toInt()}°C",
                    style = TextStyle(
                        color = Color.DarkGray,
                        fontSize = 56.sp,
                        fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier.padding(top = 20.dp)
                )
                Text(
                    text = "${weatherUIState?.weather?.weatherDescription}",
                    style = TextStyle(
                        color = Color.DarkGray,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Light
                    ),
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}

fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition()
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000)
        )
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFFB8B5B5),
                Color(0xFF8F8B8B),
                Color(0xFFB8B5B5),
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    )
        .onGloballyPositioned {
            size = it.size
        }
}


@Composable
fun ErrorAlertDialog(ex: String, onConfirmBack: () -> Unit) {
    var openDialog by remember { mutableStateOf(false) }
    AlertDialog(
        onDismissRequest = { openDialog = false },
        confirmButton = {
            TextButton(
                onClick = {
                    openDialog = false
                    onConfirmBack()
                }
            ) {
                Text(text = "OK")
            }
        },
        title = { Text(text = "Ошибка") },
        text = { Text(text = ex) }
    )
}


@Composable
@Preview
fun TempDetailsFragmentPreview() {
    TempDetailsFragment(
        city = "Kazan",
        weatherUIState = WeatherUIState(
            weather = WeatherModel( currentTemp = 23.0f, weatherDescription = "Clouds" ),
            forecast = emptyList(),
            error = null))
}