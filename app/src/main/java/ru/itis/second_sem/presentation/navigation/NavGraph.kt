package ru.itis.second_sem.presentation.navigation

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kotlinx.serialization.Serializable
import ru.itis.auth.presentation.authorization.AuthorizationEffect
import ru.itis.auth.presentation.authorization.AuthorizationRoute
import ru.itis.auth.presentation.registration.RegistrationEffect
import ru.itis.auth.presentation.registration.RegistrationRoute
import ru.itis.second_sem.presentation.screens.currentTemp.CurrentTempEffect
import ru.itis.second_sem.presentation.screens.currentTemp.CurrentTempRoute
import ru.itis.second_sem.presentation.screens.tempDetail.TempDetailsEffect
import ru.itis.second_sem.presentation.screens.tempDetail.TempDetailsRoute

sealed class Screen(val route: String) {
    object CurrentTemp : Screen("currentTemp")
    object TempDetails : Screen("tempDetails/{city}") {
        fun createRoute(city: String): String = "tempDetails/$city"
    }

    object Registration : Screen("registration")
    object Authorization : Screen("authorization")
}

@Composable
fun NavGraph(
    navHostController: NavHostController
) {
    val context = LocalContext.current

    NavHost(navController = navHostController, startDestination = Screen.Authorization.route) {
        composable(route = Screen.CurrentTemp.route) {
            CurrentTempRoute(
                onNavigate = { effect ->
                    when (effect) {
                        is CurrentTempEffect.NavigateToTempDetails -> {
                            val city = effect.city
                            navHostController.navigate(Screen.TempDetails.createRoute(city = city))
                        }
                    }
                }
            )
        }
        composable(
            route = Screen.TempDetails.route,
            arguments = listOf(navArgument("city") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val city = backStackEntry.arguments?.getString("city") ?: ""
            Log.i("CITY", city)
            TempDetailsRoute(city = city, onNavigate = { effect ->
                when (effect) {
                    is TempDetailsEffect.NavigateBack -> {
                        navHostController.popBackStack()
                    }
                }
            })
        }
        composable(route = Screen.Authorization.route) {
            AuthorizationRoute(
                onNavigate = { effect ->
                    Log.d("NAV_GRAPH", "Effect received: $effect")
                    when (effect) {
                        is AuthorizationEffect.NavigateToCurrentTemp -> {
                            navHostController.navigate(Screen.CurrentTemp.route)
                        }
                        is AuthorizationEffect.NavigateToRegister -> {
                            Log.d("NAV_GRAPH", "Navigating to registration")
                            navHostController.navigate(Screen.Registration.route)
                        }
                        is AuthorizationEffect.ShowToast -> Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }
        composable(route = Screen.Registration.route) {
            RegistrationRoute(
                onNavigate = { effect ->
                    when (effect) {
                        is RegistrationEffect.NavigateToAuthorization -> {
                            navHostController.navigate(Screen.Authorization.route)
                        }
                        is RegistrationEffect.NavigateToCurrentTemp -> {
                            navHostController.navigate(Screen.CurrentTemp.route)
                        }
                        is RegistrationEffect.ShowToast -> Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }
    }
}