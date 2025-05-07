package ru.itis.second_sem.data.database.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.itis.auth.domain.model.User
import ru.itis.auth.domain.repository.UserRepository
import ru.itis.second_sem.data.database.dao.UserDao
import ru.itis.second_sem.data.database.entity.UserEntity
import ru.itis.second_sem.utils.toEntity
import ru.itis.second_sem.utils.toUser
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val ioDispatchers: CoroutineDispatcher
): UserRepository {

    override suspend fun insertUser(user: User) {
        return withContext(ioDispatchers) {
            userDao.insertUser(user = user.toEntity())
        }
    }

    override suspend fun getUserById(id: Int): User? {
        return withContext(ioDispatchers) {
            userDao.getUserById(id)?.toUser()
        }
    }

    override suspend fun getUserByEmailAndPassword(email: String, password: String): User? {
        return withContext(ioDispatchers) {
            userDao.getUserByEmailAndPassword(email = email, password = password)?.toUser()
        }
    }

    override suspend fun getUserByEmail(email: String): User? {
        return withContext(ioDispatchers) {
            userDao.getUserByEmail(email = email)?.toUser()
        }
    }
}