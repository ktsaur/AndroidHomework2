package ru.itis.auth.domain.repository

import ru.itis.auth.domain.model.User

interface UserRepository {
    suspend fun insertUser(user: User)
    suspend fun getUserById(id: Int): User?
    suspend fun getUserByEmailAndPassword(email: String, password: String): User?
    suspend fun getUserByEmail(email: String): User?
    suspend fun getAllEmails(): List<String>?
}