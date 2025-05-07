package ru.itis.second_sem.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import ru.itis.auth.domain.model.User
import ru.itis.second_sem.data.database.entity.UserEntity
import ru.itis.second_sem.presentation.utils.AssistedFactory


inline fun <reified T : ViewModel> Fragment.lazyViewModel(noinline create: (SavedStateHandle) -> T) =
    viewModels<T> {
        AssistedFactory(this, create)
    }

fun User.toEntity() = UserEntity(userId ?: 0, email, password, username)
fun UserEntity.toUser() = User(id, email, password, username)