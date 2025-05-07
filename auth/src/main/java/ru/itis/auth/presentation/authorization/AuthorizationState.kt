package ru.itis.auth.presentation.authorization

import ru.itis.auth.utils.Result

data class AuthorizationState (
    val email: String = "",
    val password: String = "",
    val registerResult: Boolean = false,
)

sealed class AuthorizationEvent{
    data class EmailUpdate(val email: String): AuthorizationEvent()
    data class PasswordUpdate(val password: String): AuthorizationEvent()
    data object AuthorizationBtnClicked: AuthorizationEvent()
    data object RegistrationTextBtnClicked: AuthorizationEvent()
}