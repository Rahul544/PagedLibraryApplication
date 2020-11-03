package com.app.pagedlibraryapplication.source

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.app.pagedlibraryapplication.model.common.Post
import com.app.pagedlibraryapplication.model.response.UserStoryResponse
import com.app.pagedlibraryapplication.service.ApiService
import com.app.pagedlibraryapplication.service.ApiServiceBuilder
import com.app.pagedlibraryapplication.utills.NetworkState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber


class StoryDataSource : PageKeyedDataSource<Int,Post>() {

    /**
     * There is no sync on the state because paging will always call loadInitial first then wait
     * for it to return some success value before calling loadAfter.
     */
    val networkState = MutableLiveData<NetworkState>()

    val initialLoad = MutableLiveData<NetworkState>()

    val service = ApiServiceBuilder.buildService(ApiService::class.java)
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Post>
    ) {
       val call = service.getStoryPost("rahul+test1@poplify.com","bazRp8sc4zS2vjobEmmE",1, FIRST_PAGE)

        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)

        call.enqueue(object : Callback<UserStoryResponse> {
            override fun onFailure(call: Call<UserStoryResponse>, t: Throwable) {
                networkState.postValue(NetworkState.error(t.message ?: "Something went wrong!!",t.hashCode()))
            }

            override fun onResponse(
                call: Call<UserStoryResponse>,
                response: Response<UserStoryResponse>
            ) {
                if (response.isSuccessful){
                    networkState.postValue(NetworkState.LOADED)
                    initialLoad.postValue(NetworkState.LOADED)

                    val  userStoryResponse = response.body()
                    Timber.e("Hello Initial $PAGE_SIZE")
                    if (userStoryResponse!!.nextPage != null){
                        PAGE_SIZE = userStoryResponse.nextPage!!
                    }
                    val userStoryPost = userStoryResponse!!.posts

                    userStoryPost?.let {
                        callback.onResult(userStoryPost,null, FIRST_PAGE + 1)
                    }
                }else{
                    networkState.postValue(NetworkState.error(response.errorBody().toString(),response.code()))
                    initialLoad.postValue(NetworkState.error(response.errorBody().toString(),response.code()))
                }
            }
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Post>) {
        networkState.postValue(NetworkState.LOADING)
        val call = service.getStoryPost("rahul+test1@poplify.com","bazRp8sc4zS2vjobEmmE",1, params.key)
        call.enqueue(object : Callback<UserStoryResponse> {
            override fun onFailure(call: Call<UserStoryResponse>, t: Throwable) {
                networkState.postValue(NetworkState.error(t.message ?: "unknown err",t.hashCode()))
            }

            override fun onResponse(
                call: Call<UserStoryResponse>,
                response: Response<UserStoryResponse>
            ) {

                if (response.isSuccessful){
                    networkState.postValue(NetworkState.LOADED)
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
                }else{
                    networkState.postValue(NetworkState.error(response.errorBody().toString(),response.code()))
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