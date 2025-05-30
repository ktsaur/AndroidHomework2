package ru.itis.auth.presentation.registration

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.itis.auth.R


@Composable
fun RegistrationRoute(
    viewModel: RegistrationViewModel = hiltViewModel(),
    onNavigate: (RegistrationEffect) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.effectFlow.collect { effect ->
            onNavigate(effect)
        }
    }
    RegistrationContent(
        state = uiState,
        onEvent = { event ->
            viewModel.onEvent(event, context)
        })
}

@Composable
fun RegistrationContent(state: RegistrationState, onEvent: (RegistrationEvent) -> Unit) {
    val context = LocalContext.current
    Scaffold { padding ->
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = context.getString(R.string.registration_title),
                modifier = Modifier
                    .padding(top = 100.dp),
                fontSize = 28.sp,
            )
            OutlinedTextField(
                value = state.username,
                onValueChange = {
                    onEvent(RegistrationEvent.UsernameUpdate(username = it))
                },
                label = { Text(text = context.getString(R.string.username_label)) },
                modifier = Modifier.padding(top = 50.dp)
            )
            OutlinedTextField(
                value = state.email,
                onValueChange = {
                    onEvent(RegistrationEvent.EmailUpdate(email = it))
                },
                label = { Text(text = context.getString(R.string.email_label)) },
                modifier = Modifier
            )
            OutlinedTextField(
                value = state.password,
                onValueChange = {
                    onEvent(RegistrationEvent.PasswordUpdate(password = it))
                },
                label = { Text(text = context.getString(R.string.password_label)) },
                modifier = Modifier,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            )
            FilledTonalButton(
                onClick = {
                    onEvent(RegistrationEvent.RegisterBtnClicked)
                },
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier.padding(top = 70.dp)
            ) {
                Text(text = context.getString(R.string.login_button))
            }
            TextButton(
                onClick = {
                    onEvent(RegistrationEvent.LoginBtnClicked)
                },
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(top = 5.dp)
            ) {
                Text(
                    text = context.getString(R.string.already_have_account),
                    fontWeight = FontWeight.Thin,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
            }
        }
    }
}