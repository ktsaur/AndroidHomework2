package ru.itis.second_sem.presentation.navigation

import ru.itis.second_sem.R

sealed class BottomItem(val title: String, val iconId: Int, val road: String) {
    object WeatherScreen: BottomItem(title = "Weather", iconId = R.drawable.icon_weather, road = Screen.CurrentTemp.route)
    object GraphScreen: BottomItem(title = "Graph", iconId = R.drawable.icon_graph , road = Screen.Graph.route)
    object CustomViewItem: BottomItem(title= "CustomView", iconId = R.drawable.icon_pie_chart,road = Screen.CustomView.route)
}