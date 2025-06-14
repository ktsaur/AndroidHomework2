package ru.itis.auth.presentation.registration

import android.content.Context
import androidx.compose.runtime.collectAsState
import androidx.core.content.ContextCompat.getString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.itis.auth.R
import ru.itis.auth.domain.model.User
import ru.itis.auth.domain.repository.UserRepository
import ru.itis.auth.utils.AuthManager
import ru.itis.auth.utils.hashPassword
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authManager: AuthManager
): ViewModel() {

    private val _uiState = MutableStateFlow(RegistrationState())
    val uiState = _uiState.asStateFlow()

    private val _effectFlow = MutableSharedFlow<RegistrationEffect>()
    val effectFlow = _effectFlow.asSharedFlow()

    fun onEvent(event: RegistrationEvent, context: Context) {
        when(event) {
            is RegistrationEvent.UsernameUpdate -> {
                _uiState.update { it.copy(username = event.username) }
            }
            is RegistrationEvent.EmailUpdate -> {
                _uiState.update { it.copy(email = event.email) }
            }
            is RegistrationEvent.PasswordUpdate -> {
                _uiState.update { it.copy(password = event.password) }
            }
            is RegistrationEvent.RegisterBtnClicked -> {
                register(context = context)
            }
            is RegistrationEvent.LoginBtnClicked -> {
                viewModelScope.launch {
                    _effectFlow.emit(RegistrationEffect.NavigateToAuthorization)
                }
            }
        }
    }

    fun register(context: Context) {
        val username = _uiState.value.username
        val email = _uiState.value.email
        val password = _uiState.value.password

        val hashPassword = hashPassword(password)

        viewModelScope.launch {
            if (username != "" && email != "" && password != "") {
                val listEmails = userRepository.getAllEmails()
                if (listEmails?.contains(email) == true) {
                    _effectFlow.emit(RegistrationEffect.ShowToast(message = context.getString(R.string.email_taken)))
                    return@launch
                }
                if(!email.contains("@")) {
                    _effectFlow.emit(RegistrationEffect.ShowToast(message = context.getString(R.string.error_invalid_email)))
                    return@launch
                }
                if (password.length < 6 || !isValidPassword(password = password)) {
                    _effectFlow.emit(RegistrationEffect.ShowToast(message = context.getString(R.string.error_invalid_password)))
                    return@launch
                }
            } else {
                _effectFlow.emit(RegistrationEffect.ShowToast(message = context.getString(R.string.error_fill_all_fields)))
                return@launch
            }
            val user = userRepository.getUserByEmailAndPassword(email = email, password = hashPassword)
            if (user != null) {
                _uiState.update { it.copy(registerResult = false) }
                _effectFlow.emit(RegistrationEffect.ShowToast(message = context.getString(R.string.error_user_already_registered)))
            } else {
                val newUser = User(username = username, email = email, password = hashPassword)
                val userId = userRepository.insertUser(newUser)
                authManager.saveUserId(userId)
                _uiState.update { it.copy(registerResult = true) }
                _effectFlow.emit(RegistrationEffect.ShowToast(message = context.getString(R.string.success_registration)))
                _effectFlow.tryEmit(RegistrationEffect.NavigateToCurrentTemp)
            }
        }
    }

    private fun isValidPassword(password: String): Boolean {
        val passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d).+$"
        return password.matches(passwordPattern.toRegex())
    }
}