package com.app.pagedlibraryapplication.service

import com.app.pagedlibraryapplication.model.response.LoginResponse
import com.app.pagedlibraryapplication.model.response.UserStoryResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("story_posts")
    fun getStoryPost(@Header("X-User-Email")  email: String,
                     @Header("X-User-Token")  token_header: String,
                     @Query("dummypost") dummypost: Int,
                     @Query("page") page: Int): Call<UserStoryResponse>


    @POST("signin_api")
    fun login(@Body loginBody: JSONObject) : Call<LoginResponse>
}