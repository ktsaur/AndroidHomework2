package ru.itis.second_sem.presentation.base

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Scaffold
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.itis.second_sem.presentation.navigation.BottomNavigation
import ru.itis.second_sem.presentation.navigation.NavGraph
import ru.itis.second_sem.presentation.navigation.Screen

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            val navController = rememberNavController()
//            NavGraph(navHostController = navController)
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            val showBottomNavigation = currentRoute in listOf(
                Screen.Graph.route, Screen.CurrentTemp.route
            )
            Scaffold(
                bottomBar = {
                    if (showBottomNavigation) {
                        BottomNavigation(navController = navController)
                    }
                }
            ) { padding ->
                NavGraph(navHostController = navController)
            }
        }
    }
}