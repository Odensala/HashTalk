package com.odensala.hashtalk.auth.data.datasource

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.odensala.hashtalk.auth.data.mapper.toUser
import com.odensala.hashtalk.auth.domain.model.AuthState
import com.odensala.hashtalk.auth.domain.model.User
import com.odensala.hashtalk.core.domain.error.DataError
import com.odensala.hashtalk.core.domain.error.Result
import com.odensala.hashtalk.core.util.Resource
import javax.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class AuthRemoteDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRemoteDataSource {

    override suspend fun login(email: String, password: String): Resource<User> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()

            result.user?.let { firebaseUser ->
                Resource.Success(firebaseUser.toUser())
            } ?: Resource.Error("Login failed: No user data")
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun signUp(email: String, password: String): Result<Unit, DataError.Auth> {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()

            Result.Success(Unit)
        } catch (e: Exception) {
            if (e is FirebaseAuthException) {
                when (e.errorCode) {
                    "ERROR_EMAIL_ALREADY_IN_USE" -> Result.Error(DataError.Auth.EMAIL_ALREADY_IN_USE)
                    "ERROR_WEAK_PASSWORD" -> Result.Error(DataError.Auth.WEAK_PASSWORD)
                    "ERROR_INVALID_EMAIL" -> Result.Error(DataError.Auth.INVALID_EMAIL)
                    else -> Result.Error(DataError.Auth.UNKNOWN)
                }
            } else {
                Result.Error(DataError.Auth.UNKNOWN)
            }
        }
    }

    override suspend fun logout(): Resource<Unit> {
        return try {
            firebaseAuth.signOut()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Logout failed")
        }
    }

    override fun getAuthStateFlow(): Flow<AuthState> = callbackFlow {
        Log.d("Auth", "Initial user: ${firebaseAuth.currentUser?.email}")
        val listener =
            FirebaseAuth.AuthStateListener { auth ->
                val authState =
                    when (auth.currentUser) {
                        null -> AuthState.Unauthenticated
                        else -> AuthState.Authenticated
                    }
                trySend(authState)
            }

        firebaseAuth.addAuthStateListener(listener)
        awaitClose { firebaseAuth.removeAuthStateListener(listener) }
    }

    override suspend fun getCurrentUser(): User? {
        return firebaseAuth.currentUser?.toUser()
    }
}