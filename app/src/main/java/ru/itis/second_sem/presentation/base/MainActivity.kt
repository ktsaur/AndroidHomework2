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
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import dagger.hilt.android.AndroidEntryPoint
import ru.itis.auth.utils.AuthManager
import ru.itis.second_sem.R
import ru.itis.second_sem.presentation.navigation.BottomNavigation
import ru.itis.second_sem.presentation.navigation.NavGraph
import ru.itis.second_sem.presentation.navigation.NavigationManager
import ru.itis.second_sem.presentation.navigation.Routes
import ru.itis.second_sem.presentation.navigation.Screen
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var authManager: AuthManager

    @Inject
    lateinit var navigationManager: NavigationManager

    private lateinit var remoteConfig: FirebaseRemoteConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)

        val defaultFlags = mapOf(getString(R.string.remote_config_flag) to false)
        remoteConfig.setDefaultsAsync(defaultFlags)

        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Toast.makeText(this, getString(R.string.Values_not_fetched), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, getString(R.string.remote_config_received), Toast.LENGTH_SHORT).show()
            }
            val res = task.result
            println("TEST TAG - Result: $res")
        }

        val userId = authManager.getUserId()
        val initialRoute = if (userId != -1) {
            Routes.bottom_graph
        } else {
            Routes.REGISTRATION
        }
        setContent {
            val navController = rememberNavController()
            navigationManager.setNavController(navController)
            InitialNavigation(startDestination = initialRoute, navController = navController)
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
        /*Firebase.crashlytics.setCustomKeys{
            key("userId", userId.toString())
        }
        lifecycleScope.launch {
            delay(15_000L)
            throw IllegalStateException("Test crash")
        }*/
    }

    fun isFeatureEnabled(): Boolean {
        return remoteConfig.getBoolean(getString(R.string.remote_config_flag))
    }

    @Composable
    fun InitialNavigation(startDestination: String, navController: NavHostController) {
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

    override fun onDestroy() {
        super.onDestroy()
        navigationManager.clearNavController()
    }
}