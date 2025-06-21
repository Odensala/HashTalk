package com.odensala.hashtalk.timeline.di

import com.google.firebase.firestore.FirebaseFirestore
import com.odensala.hashtalk.auth.data.datasource.AuthRemoteDataSource
import com.odensala.hashtalk.timeline.data.datasource.PostsRemoteDataSource
import com.odensala.hashtalk.timeline.data.datasource.PostsRemoteDatasourceImpl
import com.odensala.hashtalk.timeline.data.repository.PostsRepositoryImpl
import com.odensala.hashtalk.timeline.domain.repository.PostsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirestoreModule {
    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideFirestoreDataSource(firebaseFirestore: FirebaseFirestore): PostsRemoteDataSource =
        PostsRemoteDatasourceImpl(firebaseFirestore)

    @Provides
    @Singleton
    fun provideFirestoreRepository(
        postsRemoteDataSource: PostsRemoteDataSource,
        authRemoteDataSource: AuthRemoteDataSource
    ): PostsRepository = PostsRepositoryImpl(postsRemoteDataSource, authRemoteDataSource)
}