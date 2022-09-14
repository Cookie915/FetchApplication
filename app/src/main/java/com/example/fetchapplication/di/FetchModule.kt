package com.example.fetchapplication.di

import com.example.fetchapplication.network.FetchApi
import com.example.fetchapplication.network.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FetchModule {
    @Singleton
    @Provides
    fun provideRetroFit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://fetch-hiring.s3.amazonaws.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitService(retrofit: Retrofit): FetchApi =
        retrofit.create(FetchApi::class.java)

    @Singleton
    @Provides
    fun provideRemoteDataSource(fetchApi: FetchApi): RemoteDataSource =
        RemoteDataSource(fetchApi)

}