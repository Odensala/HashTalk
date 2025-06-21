package com.odensala.hashtalk.auth.domain.repository

import com.odensala.hashtalk.auth.domain.model.AuthState
import com.odensala.hashtalk.auth.domain.model.User
import com.odensala.hashtalk.core.domain.error.DataError
import com.odensala.hashtalk.core.domain.error.Result
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val authState: Flow<Result<AuthState, DataError.AuthStateError>>

    suspend fun login(email: String, password: String): Result<Unit, DataError.AuthError>
    suspend fun signUp(email: String, password: String): Result<Unit, DataError.AuthError>
    suspend fun logout(): Result<Unit, DataError.AuthError>
    suspend fun getCurrentUser(): User?
}