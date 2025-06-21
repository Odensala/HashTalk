package com.odensala.hashtalk.auth.data.datasource

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.odensala.hashtalk.auth.data.mapper.toUser
import com.odensala.hashtalk.auth.domain.model.AuthState
import com.odensala.hashtalk.auth.domain.model.User
import com.odensala.hashtalk.core.domain.error.DataError
import com.odensala.hashtalk.core.domain.error.Result
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val TAG = "AuthRemoteDataSource"

class AuthRemoteDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRemoteDataSource {

    override suspend fun login(email: String, password: String): Result<Unit, DataError.AuthError> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()

            result.user?.let { firebaseUser ->
                Result.Success(Unit)
            } ?: Result.Error(DataError.AuthError.UNKNOWN)
        } catch (e: Exception) {
            Log.e(TAG, "Login failed: ${e.message}", e)

            if (e is FirebaseAuthException) {
                when (e.errorCode) {
                    "ERROR_INVALID_EMAIL" -> Result.Error(DataError.AuthError.INVALID_EMAIL)
                    "ERROR_WRONG_PASSWORD" -> Result.Error(DataError.AuthError.WRONG_PASSWORD)
                    "ERROR_USER_NOT_FOUND" -> Result.Error(DataError.AuthError.USER_NOT_FOUND)
                    else -> Result.Error(DataError.AuthError.UNKNOWN)
                }
            } else {
                Result.Error(DataError.AuthError.UNKNOWN)
            }
        }
    }

    override suspend fun signUp(email: String, password: String): Result<Unit, DataError.AuthError> {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()

            Result.Success(Unit)
        } catch (e: Exception) {
            if (e is FirebaseAuthException) {
                when (e.errorCode) {
                    "ERROR_EMAIL_ALREADY_IN_USE" -> Result.Error(DataError.AuthError.EMAIL_ALREADY_IN_USE)
                    "ERROR_WEAK_PASSWORD" -> Result.Error(DataError.AuthError.WEAK_PASSWORD)
                    "ERROR_INVALID_EMAIL" -> Result.Error(DataError.AuthError.INVALID_EMAIL)
                    else -> Result.Error(DataError.AuthError.UNKNOWN)
                }
            } else {
                Result.Error(DataError.AuthError.UNKNOWN)
            }
        }
    }

    override suspend fun logout(): Result<Unit, DataError.AuthError> {
        return try {
            firebaseAuth.signOut()
            Result.Success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Logout failed", e)
            Result.Error(DataError.AuthError.UNKNOWN)
        }
    }

    override fun getAuthStateFlow(): Flow<Result<AuthState, DataError.AuthStateError>> = callbackFlow {
        Log.d(TAG, "Initial user: ${firebaseAuth.currentUser?.email}")

        val listener =
            FirebaseAuth.AuthStateListener { auth ->
                val authState =
                    when (auth.currentUser) {
                        null -> AuthState.Unauthenticated
                        else -> AuthState.Authenticated
                    }

                trySend(Result.Success(authState))
            }

        firebaseAuth.addAuthStateListener(listener)
        awaitClose { firebaseAuth.removeAuthStateListener(listener) }
    }

    override suspend fun getCurrentUser(): User? {
        return firebaseAuth.currentUser?.toUser()
    }
}