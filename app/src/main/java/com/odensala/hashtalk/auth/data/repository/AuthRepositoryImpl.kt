package com.odensala.hashtalk.auth.data.repository

import com.odensala.hashtalk.auth.data.datasource.AuthRemoteDataSource
import com.odensala.hashtalk.auth.domain.model.AuthState
import com.odensala.hashtalk.auth.domain.model.User
import com.odensala.hashtalk.auth.domain.repository.AuthRepository
import com.odensala.hashtalk.core.domain.error.DataError
import com.odensala.hashtalk.core.domain.error.Result
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepository {

    override val authState: Flow<Result<AuthState, DataError.AuthStateError>> =
        authRemoteDataSource.getAuthStateFlow()
            .distinctUntilChanged()

    override suspend fun login(email: String, password: String): Result<Unit, DataError.AuthError> {
        return authRemoteDataSource.login(email, password)
    }

    override suspend fun signUp(email: String, password: String): Result<Unit, DataError.AuthError> {
        return authRemoteDataSource.signUp(email, password)
    }

    override suspend fun logout(): Result<Unit, DataError.AuthError> {
        return authRemoteDataSource.logout()
    }

    override suspend fun getCurrentUser(): User? {
        return authRemoteDataSource.getCurrentUser()
    }
}
