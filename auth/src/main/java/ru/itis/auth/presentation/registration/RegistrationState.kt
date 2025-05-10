package ru.itis.auth.presentation.registration

data class RegistrationState (
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val registerResult: Boolean = false,
)

sealed class RegistrationEvent{
    data class UsernameUpdate(val username: String): RegistrationEvent()
    data class EmailUpdate(val email: String): RegistrationEvent()
    data class PasswordUpdate(val password: String): RegistrationEvent()
    data object RegisterBtnClicked: RegistrationEvent()
    data object LoginBtnClicked: RegistrationEvent()
}

sealed interface RegistrationEffect{
    data object NavigateToAuthorization: RegistrationEffect
    data object NavigateToCurrentTemp: RegistrationEffect
    data class ShowToast(val message: String): RegistrationEffect
}