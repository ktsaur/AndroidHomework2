package ru.itis.second_sem.presentation.navigation

import android.util.Log
import androidx.navigation.NavController
import javax.inject.Inject

class NavigationManager @Inject constructor() {
    private var navController: NavController? = null

    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    fun clearNavController() {
        navController = null
    }

    fun getCurrentRoute(): String? {
        Log.i("NavigationManager", "current route = ${navController?.currentBackStackEntry?.destination?.route}")
        return navController?.currentBackStackEntry?.destination?.route
    }

    fun navigate(route: String) {
        navController?.let { controller ->
            try {
                val currentRoute = getCurrentRoute()
                if (currentRoute == route) {
                    return
                }
                controller.navigate(route)
            } catch (e: Exception) {
                Log.i("NavigationManager", "ошибка навигации")
            }
        } ?: Log.i("NavigationManager", "нав контроллер равен нулю")
    }
}