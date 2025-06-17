package com.odensala.hashtalk.auth.domain.repository

import com.odensala.hashtalk.auth.domain.model.AuthState
import com.odensala.hashtalk.auth.domain.model.User
import com.odensala.hashtalk.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val authState: Flow<AuthState>

    suspend fun login(
        email: String,
        password: String,
    ): Resource<User>

    suspend fun signUp(
        email: String,
        password: String,
    ): Resource<User>

    suspend fun logout(): Resource<Unit>

    suspend fun getCurrentUser(): User?
}