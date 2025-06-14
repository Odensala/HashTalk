package com.odensala.hashtalk.auth.data.datasource

import com.odensala.hashtalk.auth.domain.model.User
import com.odensala.hashtalk.core.util.Resource

interface FirebaseAuthDataSource {
    suspend fun login(
        email: String,
        password: String,
    ): Resource<User>

    suspend fun register(
        email: String,
        password: String,
    ): Resource<User>

    suspend fun logout(): Resource<Unit>

    suspend fun getCurrentUser(): User?

    fun isUserLoggedIn(): Boolean
}