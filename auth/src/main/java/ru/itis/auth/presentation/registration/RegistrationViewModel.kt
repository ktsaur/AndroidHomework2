package ru.itis.auth.presentation.registration

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.itis.auth.domain.model.User
import ru.itis.auth.domain.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    val userRepository: UserRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(RegistrationState())
    val uiState = _uiState.asStateFlow()

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
            }
        }
    }

    fun register() {
        val username = _uiState.value.username
        val email = _uiState.value.email
        val password = _uiState.value.password
        viewModelScope.launch {
            val user = userRepository.getUserByEmailAndPassword(email = email, password = password)
            if (user != null) {
                _uiState.update { it.copy(registerResult = false) }
                //показать Toast что есть такой пользователь
            } else {
                val newUser = User(username = username, email = email, password = password)
                userRepository.insertUser(newUser)
                _uiState.update { it.copy(registerResult = true) }
            }
        }
    }
}