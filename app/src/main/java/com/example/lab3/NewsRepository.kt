package com.example.lab3

import retrofit2.*
import retrofit2.http.*
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://newsdata.io/"
    fun getClient(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}

interface NewsApi {
    @GET("/api/1/news")
    suspend fun getArticles(@Query("q") request: String, @Query("apikey") key: String): Response<NewsResponse>
}

data class NewsResponse(
    val status: String?,
    val totalResults: Int?,
    val results: List<Article>?,
    val nextPage: String?
)

data class Article(
    val article_id : String?,
    val title : String?,
    val link : String?,
    val keywords : List<String>?,
    val creator : List<String>?,
    val video_url : String?,
    val description : String?,
    val content : String?,
    val pubDate : String?,
    val image_url : String?,
    val source_id : String?,
    val source_priority : Int?,
    val country	: List<String>?,
    val category : List<String>?,
    val language : String?,
)

class NewsRepository {
    private val retrofit = RetrofitClient.getClient()
    private val apiService: NewsApi = retrofit.create(NewsApi::class.java)

    suspend fun getArticles(request: String): List<Article> {
        val response = apiService.getArticles(request, Api.KEY)
        if (response.isSuccessful) {
            return response.body()?.results ?: emptyList()
        } else {
            throw ApiException(response.code(), response.message())
        }
    }
}

class ApiException(code: Int, message: String) : Exception("API request failed with code $code: $message")