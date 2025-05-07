package ru.itis.auth.presentation.authorization

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.mindrot.jbcrypt.BCrypt
import ru.itis.auth.domain.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(value = AuthorizationState())
    val uiSate = _uiState.asStateFlow()

    private val _effectFlow = MutableSharedFlow<AuthorizationEffect>()
    val effectFlow = _effectFlow.asSharedFlow()

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
                viewModelScope.launch {
                    _effectFlow.emit(AuthorizationEffect.NavigateToRegister)
                }
            }
        }
    }

    fun authUser() {
        val email = _uiState.value.email
        val password = _uiState.value.password
        viewModelScope.launch {
            val user = userRepository.getUserByEmail(email = email)
            if (user != null) {
                val storedHash = user.password
                if (BCrypt.checkpw(password, storedHash)) {
                    _uiState.update { it.copy(registerResult = true) }
                    _effectFlow.emit(AuthorizationEffect.NavigateToCurrentTemp)
                    _effectFlow.emit(AuthorizationEffect.ShowToast(message = "Авторизация прошла успешно"))
                } else {
                    _uiState.update { it.copy(registerResult = false) }
                    _effectFlow.emit(AuthorizationEffect.ShowToast(message = "Неверный пароль"))
                }
            } else {
                _uiState.update { it.copy(registerResult = false) }
                _effectFlow.emit(AuthorizationEffect.ShowToast(message = "Пользователь не найден"))
            }
            /*val user = userRepository.getUserByEmailAndPassword(email = email, password = password)
            if (user != null) {
                _uiState.update { it.copy(registerResult = true) }
                _effectFlow.emit(AuthorizationEffect.NavigateToCurrentTemp)
                _effectFlow.emit(AuthorizationEffect.ShowToast(message = "Авторизация прошла успешно"))
                //входим в аккаунт
            } else {
                _uiState.update { it.copy(registerResult = false) }
                _effectFlow.emit(AuthorizationEffect.ShowToast(message = "Сначала нужно зарегистрироваться"))
//                Toast.makeText(, ) показываем toast о том что нужно сначала зарегистрироваться
            }*/
        }
    }
}