package ru.itis.second_sem.presentation.screens.currentTemp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ru.itis.second_sem.R
import ru.itis.second_sem.presentation.navigation.Screen

@Composable
fun CurrentTempRoute(
    viewModel: CurrentTempViewModel = hiltViewModel(),
    navController: NavHostController
){
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effectFlow.collect { effect ->
            when (effect) {
                is CurrentTempEffect.NavigateToTempDetails -> {
                    navController.navigate(Screen.TempDetails.createRoute(effect.city))
                }
            }
        }
    }

    CurrentTempFragmentContent(state = uiState, onEvent = viewModel::onEvent)
}


@Composable
fun CurrentTempFragmentContent(
    state: CurrentTempUiState, onEvent: (CurrentTempEvent) -> Unit
) {
    Scaffold { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp)
        ) {
            TextField(
                value = state.city,
                onValueChange = {
                    onEvent(CurrentTempEvent.CityUpdate(city = it))
                },
                label = { Text(text = stringResource(id = R.string.enter_city)) }
            )
            OutlinedButton(
                onClick = {
                    onEvent(CurrentTempEvent.GetWeatherBtnClicked)
                },
                modifier = Modifier.padding(top = 50.dp)
            ) {
                Text(text = stringResource(id = R.string.request))
            }
        }
    }
}

