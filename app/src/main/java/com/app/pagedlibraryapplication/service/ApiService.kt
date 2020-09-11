package com.app.pagedlibraryapplication.service

import com.app.pagedlibraryapplication.model.response.UserStoryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("story_posts")
    fun getStoryPost(@Header("X-User-Email")  email: String,
                     @Header("X-User-Token")  token_header: String,
                     @Query("dummypost") dummypost: Int,
                     @Query("page") page: Int): Call<UserStoryResponse>
}