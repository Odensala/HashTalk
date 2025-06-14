package com.odensala.hashtalk.core.di

import com.google.firebase.auth.FirebaseAuth
import com.odensala.hashtalk.auth.data.repository.AuthRepositoryImpl
import com.odensala.hashtalk.auth.data.datasource.FirebaseAuthDataSource
import com.odensala.hashtalk.auth.data.datasource.FirebaseAuthDataSourceImpl
import com.odensala.hashtalk.auth.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseAuthDataSource(firebaseAuth: FirebaseAuth): FirebaseAuthDataSource = FirebaseAuthDataSourceImpl(firebaseAuth)

    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuthDataSource: FirebaseAuthDataSource): AuthRepository = AuthRepositoryImpl(firebaseAuthDataSource)
}