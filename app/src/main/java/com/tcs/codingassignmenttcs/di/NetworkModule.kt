package com.tcs.codingassignmenttcs.di

import android.content.Context
import androidx.room.Room
import com.tcs.codingassignmenttcs.api.ProductAPI
import com.tcs.codingassignmenttcs.database.ProductDao
import com.tcs.codingassignmenttcs.database.ProductDatabase
import com.tcs.codingassignmenttcs.utils.Constants.BASE_URL
import com.tcs.codingassignmenttcs.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {


    @Provides
    @Singleton
    fun providesRetrofit(@Named("Retrofit") okHttpClient: OkHttpClient): ProductAPI = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ProductAPI::class.java)

    @Provides
    @Singleton
    @Named("Retrofit")
    fun provideOkHttpClientForRetrofit(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext app: Context): ProductDao =
        Room.databaseBuilder(app, ProductDatabase::class.java, DATABASE_NAME)
            .build()
            .getDao()
}