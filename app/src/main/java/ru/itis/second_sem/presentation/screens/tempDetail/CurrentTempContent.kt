package ru.itis.second_sem.presentation.screens.tempDetail

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ru.itis.second_sem.R
import ru.itis.second_sem.presentation.base.MainActivity
import ru.itis.second_sem.presentation.navigation.Screen

@Composable
fun CurrentTempRoute(
    navController: NavHostController,
    viewModel: TempDetailsViewModel
){
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val mainActivity = context as? MainActivity
    val isFeatureEnabled = mainActivity?.isFeatureEnabled() ?: false

    LaunchedEffect(Unit) {
        viewModel.effectFlow.collect {effect ->
            when(effect) {
                is TempDetailsEffect.NavigateToTempDetails -> {
                    if (isFeatureEnabled) {
                        navController.navigate(Screen.TempDetails.route)
                    } else {
                        Toast.makeText(context, context.getString(R.string.feature_not_available), Toast.LENGTH_SHORT).show()
                    }
                }
                else -> {}
            }
        }
    }

    CurrentTempFragmentContent(state = uiState, onEvent = viewModel::onEvent)
}


@Composable
fun CurrentTempFragmentContent(
    state: WeatherUIState, onEvent: (TempDetailsEvent) -> Unit
) {
    Scaffold { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp)
                .padding(horizontal = 16.dp)
        ) {
            TextField(
                value = state.city,
                onValueChange = {
                    onEvent(TempDetailsEvent.CityUpdate(city = it))
                },
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = stringResource(id = R.string.enter_city),
                style = TextStyle(
                    color = Color.Gray,
                    fontSize = 13.sp
                ),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 5.dp)
            )
            OutlinedButton(
                onClick = {
                    onEvent(TempDetailsEvent.GetWeatherBtnClicked)
                },
                modifier = Modifier.padding(top = 50.dp)
            ) {
                Text(text = stringResource(id = R.string.request))
            }
        }
    }
}

