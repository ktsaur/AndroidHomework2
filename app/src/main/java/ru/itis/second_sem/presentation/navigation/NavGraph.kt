package ru.itis.second_sem.presentation.navigation

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ru.itis.auth.presentation.authorization.AuthorizationEffect
import ru.itis.auth.presentation.authorization.AuthorizationRoute
import ru.itis.auth.presentation.registration.RegistrationEffect
import ru.itis.auth.presentation.registration.RegistrationRoute
import ru.itis.second_sem.presentation.screens.customView.CustomViewFragment
import ru.itis.second_sem.presentation.screens.graph.GraphRoute
import ru.itis.second_sem.presentation.screens.tempDetail.CurrentTempRoute
import ru.itis.second_sem.presentation.screens.tempDetail.TempDetailsRoute
import ru.itis.second_sem.presentation.screens.tempDetail.TempDetailsViewModel
import ru.itis.second_sem.presentation.utils.sharedViewModel

object Routes {
    const val AUTHORIZATION = "authorization"
    const val REGISTRATION = "registration"
    const val CURRENT_TEMP = "currentTemp"
    const val TEMP_DETAILS = "tempDetails"
    const val GRAPH = "graph"
    const val CASTOM_VIEW = "custom_view"
    const val BOTTOM_GRAPH = "bottom_graph"
    const val WEATHER_NAVIGATION = "weather_navigation"
}

sealed class Screen(val route: String) {
    object CurrentTemp : Screen(Routes.CURRENT_TEMP)
    object CustomView : Screen(Routes.CASTOM_VIEW)
    object TempDetails : Screen(Routes.TEMP_DETAILS)
    object Graph : Screen(Routes.GRAPH)
    object Registration : Screen(Routes.REGISTRATION)
    object Authorization : Screen(Routes.AUTHORIZATION)
}

@Composable
fun NavGraph(
    navHostController: NavHostController,
    startDestination: String
) {
    val context = LocalContext.current

    NavHost(
        navController = navHostController,
        startDestination = startDestination
    ) {
        navigation(
            route = Routes.BOTTOM_GRAPH,
            startDestination = Routes.WEATHER_NAVIGATION
        ) {
            navigation(
                route = Routes.WEATHER_NAVIGATION,
                startDestination = Screen.CurrentTemp.route
            ) {
                composable(route = Screen.CurrentTemp.route) { backStackEntry ->
                    val viewModel = backStackEntry.sharedViewModel<TempDetailsViewModel>(
                        navController = navHostController,
                        navGraphRoute = Routes.WEATHER_NAVIGATION,
                        navBackStackEntry = backStackEntry
                    )
                    CurrentTempRoute(navController = navHostController, viewModel = viewModel)
                }
                composable(route = Screen.TempDetails.route) { backStackEntry ->
                    val viewModel = backStackEntry.sharedViewModel<TempDetailsViewModel>(
                        navController = navHostController,
                        navGraphRoute = Routes.WEATHER_NAVIGATION,
                        navBackStackEntry = backStackEntry
                    )
                    TempDetailsRoute(navController = navHostController, viewModel = viewModel)
                }
            }
            composable(route = Screen.Graph.route) {
                GraphRoute()
            }
        }
        composable(route = Screen.CustomView.route) {
            FragmentContainer(
                modifier = Modifier.fillMaxSize(),
                fragmentClass = CustomViewFragment::class.java
            )
        }
        composable(route = Screen.Authorization.route) {
            AuthorizationRoute(
                onNavigate = { effect ->
                    when (effect) {
                        is AuthorizationEffect.NavigateToCurrentTemp -> {
                            navHostController.navigate(Routes.WEATHER_NAVIGATION)
                        }

                        is AuthorizationEffect.NavigateToRegister -> {
                            navHostController.navigate(Screen.Registration.route)
                        }

                        is AuthorizationEffect.ShowToast -> Toast.makeText(
                            context,
                            effect.message,
                            Toast.LENGTH_SHORT
                        ).show()
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
                            navHostController.navigate(Routes.WEATHER_NAVIGATION)
                        }

                        is RegistrationEffect.ShowToast -> Toast.makeText(
                            context,
                            effect.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            )
        }
    }
}