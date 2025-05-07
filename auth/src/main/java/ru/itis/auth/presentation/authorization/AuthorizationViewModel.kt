package ru.itis.auth.presentation.authorization

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.itis.auth.domain.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(value = AuthorizationState())
    val uiSate = _uiState.asStateFlow()

    fun onEvent(event: AuthorizationEvent) {
        when(event) {
            is AuthorizationEvent.EmailUpdate ->
                _uiState.update { it.copy(email = event.email) }
            is AuthorizationEvent.PasswordUpdate ->
                _uiState.update { it.copy(password = event.password) }
            is AuthorizationEvent.AuthorizationBtnClicked -> {
                // тут навигация
                authUser()
            }
            is AuthorizationEvent.RegistrationTextBtnClicked -> {
                // навигация на экран регистрации
            }
        }
    }

    fun authUser() {
        val email = _uiState.value.email
        val password = _uiState.value.password
        viewModelScope.launch {
            val user = userRepository.getUserByEmailAndPassword(email = email, password = password)
            if (user != null) {
                _uiState.update { it.copy(registerResult = true) }
                //выходим в аккаунт
            } else {
                _uiState.update { it.copy(registerResult = false) }
//                Toast.makeText(, ) показываем toast о том что нужно сначала зарегистрироваться
            }
        }
    }
}