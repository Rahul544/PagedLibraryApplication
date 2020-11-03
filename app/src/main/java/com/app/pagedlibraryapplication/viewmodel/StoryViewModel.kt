package com.app.pagedlibraryapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.app.pagedlibraryapplication.factory.StoryDataSourceFactory
import com.app.pagedlibraryapplication.model.common.Post
import com.app.pagedlibraryapplication.model.response.LoginResponse
import com.app.pagedlibraryapplication.source.StoryDataSource
import com.app.pagedlibraryapplication.utills.NetworkState

class StoryViewModel : ViewModel() {


    var initialState: LiveData<NetworkState>
    var networkState: LiveData<NetworkState>
    var storyPagedList: LiveData<PagedList<Post>>
    var liveDataSource: LiveData<StoryDataSource>

    //Testing purpose
//    var loginResponse :  LiveData<LoginResponse>


    init {
        val storyDataSourceFactory = StoryDataSourceFactory()
        liveDataSource = storyDataSourceFactory.storyLiveDataSource
        networkState = Transformations.switchMap(liveDataSource) { it.networkState }
        initialState = Transformations.switchMap(liveDataSource) { it.initialLoad }

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(StoryDataSource.PAGE_SIZE)
            .build()

        storyPagedList = LivePagedListBuilder(storyDataSourceFactory, config).build()

    }

    fun refresh() {
        liveDataSource.value?.invalidate()
    }


}