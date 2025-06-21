package com.odensala.hashtalk.auth.data.datasource

import com.odensala.hashtalk.auth.domain.model.AuthState
import com.odensala.hashtalk.auth.domain.model.User
import com.odensala.hashtalk.core.domain.error.DataError
import com.odensala.hashtalk.core.domain.error.Result
import com.odensala.hashtalk.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRemoteDataSource {
    suspend fun login(email: String, password: String): Resource<User>
    suspend fun signUp(email: String, password: String): Result<Unit, DataError.Auth>
    suspend fun logout(): Resource<Unit>
    fun getAuthStateFlow(): Flow<AuthState>
    suspend fun getCurrentUser(): User?
}