package ru.itis.auth.presentation.registration

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.itis.auth.domain.model.User
import ru.itis.auth.domain.repository.UserRepository
import ru.itis.auth.utils.hashPassword
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    val userRepository: UserRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(RegistrationState())
    val uiState = _uiState.asStateFlow()

    private val _effectFlow = MutableSharedFlow<RegistrationEffect>()
    val effectFlow = _effectFlow.asSharedFlow()

    fun onEvent(event: RegistrationEvent) {
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
                register()
            }
            is RegistrationEvent.LoginBtnClicked -> {
                //навигация на экран авторизации
                viewModelScope.launch {
                    _effectFlow.emit(RegistrationEffect.NavigateToAuthorization)
                }
            }
        }
    }

    fun register() {
        val username = _uiState.value.username
        val email = _uiState.value.email
        val password = _uiState.value.password

        val hashPassword = hashPassword(password)

        viewModelScope.launch {
            if (username != "" && email != "" && password != "") {
                if(!email.contains("@")) {
                    _effectFlow.emit(RegistrationEffect.ShowToast(message = "Введите корректный email"))
                    return@launch
                }
                if (password.length < 6 || !isValidPassword(password = password)) {
                    _effectFlow.emit(RegistrationEffect.ShowToast(message = "Пароль должен содержать минимум 6 симолов, буквы и цифры"))
                    return@launch
                }
            } else {
                _effectFlow.emit(RegistrationEffect.ShowToast(message = "Все поля должны быть заполнены!"))
                return@launch
            }
            val user = userRepository.getUserByEmailAndPassword(email = email, password = hashPassword)
            if (user != null) {
                _uiState.update { it.copy(registerResult = false) }
                _effectFlow.emit(RegistrationEffect.ShowToast(message = "Такой пользователь уэе зарегистрирован"))
            } else {
                val newUser = User(username = username, email = email, password = hashPassword)
                userRepository.insertUser(newUser)
                _uiState.update { it.copy(registerResult = true) }
                _effectFlow.emit(RegistrationEffect.ShowToast(message = "Регистрация прошла успешно"))
                _effectFlow.tryEmit(RegistrationEffect.NavigateToCurrentTemp)
            }
        }
    }

    private fun isValidPassword(password: String): Boolean {
        val passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d).+$"
        return password.matches(passwordPattern.toRegex())
    }
}