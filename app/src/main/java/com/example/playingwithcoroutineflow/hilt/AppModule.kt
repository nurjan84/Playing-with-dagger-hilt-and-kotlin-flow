package com.example.playingwithcoroutineflow.hilt

import com.example.playingwithcoroutineflow.BuildConfig
import com.example.playingwithcoroutineflow.api.ExampleApi
import com.example.playingwithcoroutineflow.repos.Repository
import com.example.playingwithcoroutineflow.room.dao.ExampleDao
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun retrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun  getApiService(retrofit: Retrofit): ExampleApi {
        return retrofit.create(ExampleApi::class.java)
    }

    @ExperimentalCoroutinesApi
    @Singleton
    @Provides
    fun  getRepo(api: ExampleApi, exampleDao: ExampleDao): Repository {
        return Repository(api,exampleDao)
    }
}