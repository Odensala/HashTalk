package com.odensala.hashtalk.auth.data.repository

import com.odensala.hashtalk.auth.data.datasource.FirebaseAuthDataSource
import com.odensala.hashtalk.auth.domain.repository.AuthRepository
import com.odensala.hashtalk.auth.domain.model.User
import com.odensala.hashtalk.core.util.Resource

class AuthRepositoryImpl(
    private val firebaseAuthDataSource: FirebaseAuthDataSource,
) : AuthRepository {
    override suspend fun login(
        email: String,
        password: String,
    ): Resource<User> {
        return firebaseAuthDataSource.login(email, password)
    }

    override suspend fun register(
        email: String,
        password: String,
    ): Resource<User> {
        return firebaseAuthDataSource.register(email, password)
    }

    override suspend fun logout(): Resource<Unit> {
        return firebaseAuthDataSource.logout()
    }

    override suspend fun getCurrentUser(): User? {
        return firebaseAuthDataSource.getCurrentUser()
    }

    override fun isUserLoggedIn(): Boolean {
        return firebaseAuthDataSource.isUserLoggedIn()
    }
}
