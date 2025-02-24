package com.example.videoplayer.di

import android.content.Context
import androidx.room.Room
import com.example.videoplayer.BuildConfig
import com.example.videoplayer.data.AppDatabase
import com.example.videoplayer.data.VideoApiService
import com.example.videoplayer.data.VideoRepositoryImpl
import com.example.videoplayer.domain.repository.VideoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideVideoApiService(): VideoApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.coverr.co/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VideoApiService::class.java)
    }

    @Provides
    @Named("apiKey")
    fun provideApiKey(): String{
        return BuildConfig.API_KEY
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "video-db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideVideoRepository(
        apiService: VideoApiService,
        @Named("apiKey") apiKey: String,
        database: AppDatabase
    ):VideoRepository {
        return VideoRepositoryImpl(apiService, apiKey, database.videoDao())
    }
}