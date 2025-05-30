package ru.itis.auth.presentation.authorization

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.itis.auth.R

@Composable
fun AuthorizationRoute(
    viewModel: AuthorizationViewModel = hiltViewModel(),
    onNavigate: (AuthorizationEffect) -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.uiSate.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.effectFlow.collect { effect ->
            onNavigate(effect)
        }
    }
    AuthorizationContent(state = state,
        onEvent = { event ->
            viewModel.onEvent(event, context)
        })
}

@Composable
@Preview
fun AuthorizationPreview() {
    AuthorizationContent(state = AuthorizationState(), onEvent = { })
}


@Composable
private fun AuthorizationContent(state: AuthorizationState, onEvent: (AuthorizationEvent) -> Unit) {
    Scaffold { preview ->
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.authorization_title),
                modifier = Modifier
                    .padding(top = 100.dp),
                fontSize = 28.sp,
            )
            OutlinedTextField(
                value = state.email,
                onValueChange = {
                    onEvent(AuthorizationEvent.EmailUpdate(email = it))
                },
                label = { Text(text = stringResource(R.string.email_label)) },
                modifier = Modifier.padding(top = 50.dp)
            )
            OutlinedTextField(
                value = state.password,
                onValueChange = {
                    onEvent(AuthorizationEvent.PasswordUpdate(password = it))
                },
                label = { Text(text = stringResource(R.string.password_label)) },
                modifier = Modifier,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            FilledTonalButton(
                onClick = {
                    onEvent(AuthorizationEvent.AuthorizationBtnClicked)
                },
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier.padding(top = 70.dp)
            ) {
                Text(text = stringResource(R.string.login_button))
            }
            TextButton(
                onClick = {
                    onEvent(AuthorizationEvent.RegistrationTextBtnClicked)
                },
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(top = 5.dp)
            ) {
                Text(
                    text = stringResource(R.string.register_button),
                    fontWeight = FontWeight.Thin,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
            }
        }
    }
}