package ru.itis.second_sem.presentation.ui

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.itis.second_sem.R
import ru.itis.second_sem.domain.model.ForecastModel


@Composable
fun TempDetailsFragment(city: String?, temperature: Float?, description: String?, forecast: List<ForecastModel>?) {
    Scaffold(containerColor = colorResource(id = R.color.blue)) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
                            text = "${temperature?.toInt()}°C",
                            style = TextStyle(
                                color = Color.DarkGray,
                                fontSize = 56.sp,
                                fontWeight = FontWeight.SemiBold
                            ),
                            modifier = Modifier.padding(top = 20.dp)
                        )
                        Text(
                            text = "$description",
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
                        forecast?.let {
                            itemsIndexed(
                                it.subList(0, 8)
                            ) { _, item ->
                                ItemRow(item)
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
                        forecast?.let {
                            itemsIndexed(
                                it.subList(8, 16)
                            ) { _, item ->
                                ItemRow(item)
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
                        forecast?.let {
                            itemsIndexed(
                                it.subList(16, 24)
                            ) { _, item ->
                                ItemRow(item)
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
                Text(text = "${forecastModel.dt}", fontSize = 14.sp)
                Text(text = "${forecastModel.temp?.toInt()}°C",fontSize = 18.sp, modifier = Modifier.padding(top = 3.dp))
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
                    "temp: ${forecastModel.temp?.toInt()} \n" +
                    "feels like: ${forecastModel.feelsLike?.toInt()} \n" +
                    "max temp: ${forecastModel.tempMax?.toInt()} \n" +
                    "min temp: ${forecastModel.tempMin?.toInt()} \n" +
                    "description: ${forecastModel.mainDesc} \n"
            ) }
        )
    }
}


@Composable
@Preview
fun TempDetailsFragmentPreview() {
    TempDetailsFragment(city = "Kazan", temperature = 23.0f, description = "broken clouds", forecast = listOf(
        ForecastModel(dt = "00:00",
            temp = 12.0f,
            feelsLike = 13.0f,
            tempMin = 17.0f,
            tempMax = 23.0f,
            mainDesc = "Cloud",
            description = "Very cloud"
        ),
        ForecastModel(dt = "03:00",
            temp = 12.0f,
            feelsLike = 13.0f,
            tempMin = 17.0f,
            tempMax = 23.0f,
            mainDesc = "Cloud",
            description = "Very cloud"
        ),
        ForecastModel(dt = "06:00",
            temp = 12.0f,
            feelsLike = 13.0f,
            tempMin = 17.0f,
            tempMax = 23.0f,
            mainDesc = "Cloud",
            description = "Very cloud"
        ),
        ForecastModel(dt = "09:00",
            temp = 12.0f,
            feelsLike = 13.0f,
            tempMin = 17.0f,
            tempMax = 23.0f,
            mainDesc = "Cloud",
            description = "Very cloud"
        ),
        ForecastModel(dt = "12:00",
            temp = 12.0f,
            feelsLike = 13.0f,
            tempMin = 17.0f,
            tempMax = 23.0f,
            mainDesc = "Cloud",
            description = "Very cloud"
        ),
        ForecastModel(dt = "15:00",
            temp = 12.0f,
            feelsLike = 13.0f,
            tempMin = 17.0f,
            tempMax = 23.0f,
            mainDesc = "Cloud",
            description = "Very cloud"
        ),
        ForecastModel(dt = "18:00",
            temp = 12.0f,
            feelsLike = 13.0f,
            tempMin = 17.0f,
            tempMax = 23.0f,
            mainDesc = "Cloud",
            description = "Very cloud"
        ),
        ForecastModel(dt = "21:00",
            temp = 12.0f,
            feelsLike = 13.0f,
            tempMin = 17.0f,
            tempMax = 23.0f,
            mainDesc = "Cloud",
            description = "Very cloud"
        ),
        ForecastModel(dt = "00:00",
            temp = 12.0f,
            feelsLike = 13.0f,
            tempMin = 17.0f,
            tempMax = 23.0f,
            mainDesc = "Cloud",
            description = "Very cloud"
        ),
        ForecastModel(dt = "03:00",
            temp = 12.0f,
            feelsLike = 13.0f,
            tempMin = 17.0f,
            tempMax = 23.0f,
            mainDesc = "Cloud",
            description = "Very cloud"
        ),
        ForecastModel(dt = "06:00",
            temp = 12.0f,
            feelsLike = 13.0f,
            tempMin = 17.0f,
            tempMax = 23.0f,
            mainDesc = "Cloud",
            description = "Very cloud"
        ),
        ForecastModel(dt = "09:00",
            temp = 12.0f,
            feelsLike = 13.0f,
            tempMin = 17.0f,
            tempMax = 23.0f,
            mainDesc = "Cloud",
            description = "Very cloud"
        ),
        ForecastModel(dt = "12:00",
            temp = 12.0f,
            feelsLike = 13.0f,
            tempMin = 17.0f,
            tempMax = 23.0f,
            mainDesc = "Cloud",
            description = "Very cloud"
        ),
        ForecastModel(dt = "15:00",
            temp = 12.0f,
            feelsLike = 13.0f,
            tempMin = 17.0f,
            tempMax = 23.0f,
            mainDesc = "Cloud",
            description = "Very cloud"
        ),
        ForecastModel(dt = "18:00",
            temp = 12.0f,
            feelsLike = 13.0f,
            tempMin = 17.0f,
            tempMax = 23.0f,
            mainDesc = "Cloud",
            description = "Very cloud"
        ),
        ForecastModel(dt = "21:00",
            temp = 12.0f,
            feelsLike = 13.0f,
            tempMin = 17.0f,
            tempMax = 23.0f,
            mainDesc = "Cloud",
            description = "Very cloud"
        ),
        ForecastModel(dt = "00:00",
            temp = 12.0f,
            feelsLike = 13.0f,
            tempMin = 17.0f,
            tempMax = 23.0f,
            mainDesc = "Cloud",
            description = "Very cloud"
        ),
        ForecastModel(dt = "03:00",
            temp = 12.0f,
            feelsLike = 13.0f,
            tempMin = 17.0f,
            tempMax = 23.0f,
            mainDesc = "Cloud",
            description = "Very cloud"
        ),
        ForecastModel(dt = "06:00",
            temp = 12.0f,
            feelsLike = 13.0f,
            tempMin = 17.0f,
            tempMax = 23.0f,
            mainDesc = "Cloud",
            description = "Very cloud"
        ),
        ForecastModel(dt = "09:00",
            temp = 12.0f,
            feelsLike = 13.0f,
            tempMin = 17.0f,
            tempMax = 23.0f,
            mainDesc = "Cloud",
            description = "Very cloud"
        ),
        ForecastModel(dt = "12:00",
            temp = 12.0f,
            feelsLike = 13.0f,
            tempMin = 17.0f,
            tempMax = 23.0f,
            mainDesc = "Cloud",
            description = "Very cloud"
        ),
        ForecastModel(dt = "15:00",
            temp = 12.0f,
            feelsLike = 13.0f,
            tempMin = 17.0f,
            tempMax = 23.0f,
            mainDesc = "Cloud",
            description = "Very cloud"
        ),
        ForecastModel(dt = "18:00",
            temp = 12.0f,
            feelsLike = 13.0f,
            tempMin = 17.0f,
            tempMax = 23.0f,
            mainDesc = "Cloud",
            description = "Very cloud"
        ),
        ForecastModel(dt = "21:00",
            temp = 12.0f,
            feelsLike = 13.0f,
            tempMin = 17.0f,
            tempMax = 23.0f,
            mainDesc = "Cloud",
            description = "Very cloud"
        )
    ))
}