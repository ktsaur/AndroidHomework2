package ru.itis.second_sem.presentation.base

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.itis.auth.presentation.authorization.AuthorizationFragment
import ru.itis.second_sem.R
import ru.itis.second_sem.presentation.navigation.NavGraph
import ru.itis.second_sem.presentation.screens.currentTemp.CurrentTempFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavGraph(navHostController = navController)
        }
    }
}