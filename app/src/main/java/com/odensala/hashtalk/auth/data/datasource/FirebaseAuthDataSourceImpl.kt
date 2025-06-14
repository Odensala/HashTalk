package com.odensala.hashtalk.auth.data.datasource

import com.google.firebase.auth.FirebaseAuth
import com.odensala.hashtalk.auth.data.mapper.toUser
import com.odensala.hashtalk.auth.domain.model.User
import com.odensala.hashtalk.core.util.Resource
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

    override suspend fun register(
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

    override suspend fun getCurrentUser(): User? {
        return firebaseAuth.currentUser?.toUser()
    }

    override fun isUserLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }
}