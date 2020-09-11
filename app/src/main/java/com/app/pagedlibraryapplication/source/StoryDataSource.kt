package com.app.pagedlibraryapplication.source

import androidx.paging.PageKeyedDataSource
import com.app.pagedlibraryapplication.model.common.Post
import com.app.pagedlibraryapplication.model.response.UserStoryResponse
import com.app.pagedlibraryapplication.service.ApiService
import com.app.pagedlibraryapplication.service.ApiServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber


class StoryDataSource : PageKeyedDataSource<Int,Post>() {
    val service = ApiServiceBuilder.buildService(ApiService::class.java)
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Post>
    ) {
       val call = service.getStoryPost("rahul+test1@poplify.com","bazRp8sc4zS2vjobEmmE",1, FIRST_PAGE)
        call.enqueue(object : Callback<UserStoryResponse> {
            override fun onFailure(call: Call<UserStoryResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(
                call: Call<UserStoryResponse>,
                response: Response<UserStoryResponse>
            ) {
                if (response.isSuccessful){
                    val  userStoryResponse = response.body()
                    Timber.e("Hello Initial $PAGE_SIZE")
                    if (userStoryResponse!!.nextPage != null){
                        PAGE_SIZE = userStoryResponse.nextPage!!
                    }
                    val userStoryPost = userStoryResponse!!.posts

                    userStoryPost?.let {
                        callback.onResult(userStoryPost,null, FIRST_PAGE + 1)
                    }
                }

            }

        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Post>) {
        val call = service.getStoryPost("rahul+test1@poplify.com","bazRp8sc4zS2vjobEmmE",1, params.key)
        call.enqueue(object : Callback<UserStoryResponse> {
            override fun onFailure(call: Call<UserStoryResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(
                call: Call<UserStoryResponse>,
                response: Response<UserStoryResponse>
            ) {

                if (response.isSuccessful){
                    val  userStoryResponse = response.body()

                    if (userStoryResponse!!.nextPage != null){
                        PAGE_SIZE = userStoryResponse.nextPage!!
                    }
                    Timber.e("Hello after $PAGE_SIZE")
                    val userStoryPost = userStoryResponse.posts
                    val key = params.key + 1
                    userStoryPost?.let {
                        callback.onResult(userStoryPost, key)
                    }
                }

            }

        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Post>) {

    }

    companion object {
        const val FIRST_PAGE = 1
        var PAGE_SIZE = 1
    }
}