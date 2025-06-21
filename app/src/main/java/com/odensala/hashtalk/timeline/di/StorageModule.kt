package com.odensala.hashtalk.timeline.di

import com.google.firebase.storage.FirebaseStorage
import com.odensala.hashtalk.timeline.data.datasource.ImagesRemoteDataSource
import com.odensala.hashtalk.timeline.data.datasource.ImagesRemoteDataSourceImpl
import com.odensala.hashtalk.timeline.data.repository.ImagesRepositoryImpl
import com.odensala.hashtalk.timeline.domain.repository.ImagesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {
    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage = FirebaseStorage.getInstance()

    @Provides
    @Singleton
    fun provideImagesRemoteDataSource(firebaseStorage: FirebaseStorage): ImagesRemoteDataSource =
        ImagesRemoteDataSourceImpl(firebaseStorage)

    @Provides
    @Singleton
    fun provideImagesRepository(imagesRemoteDataSource: ImagesRemoteDataSource): ImagesRepository =
        ImagesRepositoryImpl(imagesRemoteDataSource)
}