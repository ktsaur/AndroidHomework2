package ru.itis.second_sem.presentation.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kotlinx.serialization.Serializable
import ru.itis.second_sem.presentation.screens.currentTemp.CurrentTempRoute
import ru.itis.second_sem.presentation.screens.tempDetail.TempDetailsRoute

sealed class Screen(val route: String) {
    object CurrentTemp : Screen("currentTemp")
    object TempDetails : Screen("tempDetails/{city}") {
        fun createRoute(city: String): String = "tempDetails/$city"
    }
}

@Composable
fun NavGraph(
    navHostController: NavHostController
) {
    NavHost(navController = navHostController, startDestination = Screen.CurrentTemp.route) {
        composable(route = Screen.CurrentTemp.route) {
            CurrentTempRoute(
                navController = navHostController
            )
        }
        composable(
            route =  Screen.TempDetails.route,
            arguments = listOf(navArgument("city") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val city = backStackEntry.arguments?.getString("city") ?: ""
            Log.i("CITY", "$city")
            TempDetailsRoute(city = city, navController = navHostController)
        }
    }
}