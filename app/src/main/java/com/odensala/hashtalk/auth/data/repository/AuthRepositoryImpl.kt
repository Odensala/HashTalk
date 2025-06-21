package com.odensala.hashtalk.auth.data.repository

import com.odensala.hashtalk.auth.data.datasource.AuthRemoteDataSource
import com.odensala.hashtalk.auth.domain.model.AuthState
import com.odensala.hashtalk.auth.domain.model.User
import com.odensala.hashtalk.auth.domain.repository.AuthRepository
import com.odensala.hashtalk.core.domain.error.DataError
import com.odensala.hashtalk.core.domain.error.Result
import com.odensala.hashtalk.core.util.Resource
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepository {

    override val authState: Flow<AuthState> =
        authRemoteDataSource.getAuthStateFlow()
            .distinctUntilChanged()

    override suspend fun login(email: String, password: String): Resource<User> {
        return authRemoteDataSource.login(email, password)
    }

    override suspend fun signUp(email: String, password: String): Result<Unit, DataError.Auth> {
        return authRemoteDataSource.signUp(email, password)
    }

    override suspend fun logout(): Resource<Unit> {
        return authRemoteDataSource.logout()
    }

    override suspend fun getCurrentUser(): User? {
        return authRemoteDataSource.getCurrentUser()
    }
}
