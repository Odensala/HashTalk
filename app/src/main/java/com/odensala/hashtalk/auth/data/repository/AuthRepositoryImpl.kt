package com.odensala.hashtalk.auth.data.repository

import com.odensala.hashtalk.auth.data.datasource.FirebaseAuthDataSource
import com.odensala.hashtalk.auth.domain.model.AuthState
import com.odensala.hashtalk.auth.domain.model.User
import com.odensala.hashtalk.auth.domain.repository.AuthRepository
import com.odensala.hashtalk.core.domain.error.DataError
import com.odensala.hashtalk.core.domain.error.Result
import com.odensala.hashtalk.core.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class AuthRepositoryImpl
    @Inject
    constructor(private val dataSource: FirebaseAuthDataSource) :
    AuthRepository {
        override val authState: Flow<AuthState> =
            dataSource.getAuthStateFlow()
                .distinctUntilChanged()

        override suspend fun login(
            email: String,
            password: String,
        ): Resource<User> {
            return dataSource.login(email, password)
        }

        override suspend fun signUp(
            email: String,
            password: String,
        ): Result<Unit, DataError.Auth> {
            return dataSource.signUp(email, password)
        }

        override suspend fun logout(): Resource<Unit> {
            return dataSource.logout()
        }

        override suspend fun getCurrentUser(): User? {
            return dataSource.getCurrentUser()
        }
    }
