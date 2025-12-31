package com.halawany.innovationteamtaskpostsapp.data.remote

import com.halawany.innovationteamtaskpostsapp.data.remote.dto.NewsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface PostApiService {
    @GET("top-headlines")
    suspend fun getPosts(
            @Query("query") query: String = "Technology",
            @Query("country") country: String = "US",
            @Query("lang") lang: String = "en"
    ): NewsResponseDto
}
