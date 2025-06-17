package com.odensala.hashtalk.auth.data.datasource

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.odensala.hashtalk.auth.data.mapper.toUser
import com.odensala.hashtalk.auth.domain.model.AuthState
import com.odensala.hashtalk.auth.domain.model.User
import com.odensala.hashtalk.core.util.Resource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseAuthDataSourceImpl(
    private val firebaseAuth: FirebaseAuth,
) : FirebaseAuthDataSource {
    override suspend fun login(
        email: String,
        password: String,
    ): Resource<User> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()

            result.user?.let { firebaseUser ->
                Resource.Success(firebaseUser.toUser())
            } ?: Resource.Error("Login failed: No user data")
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun signUp(
        email: String,
        password: String,
    ): Resource<User> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()

            result.user?.let { firebaseUser ->
                Resource.Success(firebaseUser.toUser())
            } ?: Resource.Error("Registration failed: No user data")
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error occurred")
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

    override fun observeAuthState(): Flow<AuthState> =
        callbackFlow {
            Log.d("Auth", "Initial user: ${firebaseAuth.currentUser?.email}")
            val authStateListener =
                FirebaseAuth.AuthStateListener { auth ->
                    val authState =
                        when (auth.currentUser) {
                            null -> AuthState.Unauthenticated
                            else -> AuthState.Authenticated
                        }
                    trySend(authState)
                }

            firebaseAuth.addAuthStateListener(authStateListener)
            awaitClose { firebaseAuth.removeAuthStateListener(authStateListener) }
        }

    override suspend fun getCurrentUser(): User? {
        return firebaseAuth.currentUser?.toUser()
    }
}