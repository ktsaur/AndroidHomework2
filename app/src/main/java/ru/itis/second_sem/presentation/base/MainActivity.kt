package ru.itis.second_sem.presentation.base

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Contextual
import ru.itis.auth.utils.AuthManager
import ru.itis.second_sem.R
import ru.itis.second_sem.presentation.navigation.BottomNavigation
import ru.itis.second_sem.presentation.navigation.NavGraph
import ru.itis.second_sem.presentation.navigation.Routes
import ru.itis.second_sem.presentation.navigation.Screen
import ru.itis.second_sem.utils.ActivityLifecycleHandler
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var authManager: AuthManager

    companion object {
        var navController: NavHostController? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userId = authManager.getUserId()
        Log.i("MAIN-ACTIVITY", "userId = $userId")
        val initialRoute = if (userId != -1) {
            Routes.bottom_graph
        } else {
            Routes.REGISTRATION
        }
        setContent {
            val navController = rememberNavController()
            MainActivity.navController = navController
            initialNavigation(startDestination = initialRoute, navController = navController)
        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.i("MAIN-TAG", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            Log.i("MAIN-ACTIVITY", "$token")
        })
    }
}

@Composable
fun initialNavigation(startDestination: String, navController: NavHostController) {
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
        NavGraph(navHostController = navController, startDestination = startDestination)
    }
}