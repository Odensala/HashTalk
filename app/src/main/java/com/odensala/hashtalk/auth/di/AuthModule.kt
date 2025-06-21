package com.odensala.hashtalk.auth.di

import com.google.firebase.auth.FirebaseAuth
import com.odensala.hashtalk.auth.data.datasource.AuthRemoteDataSource
import com.odensala.hashtalk.auth.data.datasource.AuthRemoteDataSourceImpl
import com.odensala.hashtalk.auth.data.repository.AuthRepositoryImpl
import com.odensala.hashtalk.auth.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseAuthDataSource(firebaseAuth: FirebaseAuth): AuthRemoteDataSource = AuthRemoteDataSourceImpl(firebaseAuth)

    @Provides
    @Singleton
    fun provideAuthRepository(authRemoteDataSource: AuthRemoteDataSource): AuthRepository = AuthRepositoryImpl(authRemoteDataSource)
}