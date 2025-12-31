package com.halawany.innovationteamtaskpostsapp.di

import android.app.Application
import androidx.room.Room
import com.halawany.innovationteamtaskpostsapp.data.local.PostDatabase
import com.halawany.innovationteamtaskpostsapp.data.remote.PostApiService
import com.halawany.innovationteamtaskpostsapp.data.repository.PostRepositoryImpl
import com.halawany.innovationteamtaskpostsapp.domain.repository.PostRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePostDatabase(app: Application): PostDatabase {
        return Room.databaseBuilder(app, PostDatabase::class.java, "posts_db").build()
    }

    @Provides
    @Singleton
    fun providePostApiService(): PostApiService {
        return Retrofit.Builder()
            .baseUrl("https://real-time-news-data.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(authenticationInterceptor())
                    .addInterceptor(loggingInterceptor())
                    .build()
            )
            .build()
            .create(PostApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePostRepository(api: PostApiService, db: PostDatabase): PostRepository {
        return PostRepositoryImpl(api, db)
    }
}

private fun authenticationInterceptor(): (Interceptor.Chain) -> Response = { chain ->
    val request =
        chain.request()
            .newBuilder()
            .addHeader(
                "x-rapidapi-key",
                "c9ad1973cfmsh551a25db61acd9ap16ef38jsn45c817d30aee"
            )

            .addHeader(
                "x-rapidapi-host",
                "real-time-news-data.p.rapidapi.com"
            )
            .build()
    chain.proceed(request)
}

private fun loggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

