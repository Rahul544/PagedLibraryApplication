package com.app.pagedlibraryapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.app.pagedlibraryapplication.factory.StoryDataSourceFactory
import com.app.pagedlibraryapplication.model.common.Post
import com.app.pagedlibraryapplication.source.StoryDataSource

class StoryViewModel : ViewModel() {

    var storyPagedList: LiveData<PagedList<Post>>
    var liveDataSource: LiveData<StoryDataSource>

    init {
        val storyDataSourceFactory = StoryDataSourceFactory()
        liveDataSource = storyDataSourceFactory.storyLiveDataSource

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(StoryDataSource.PAGE_SIZE)
            .build()

        storyPagedList = LivePagedListBuilder(storyDataSourceFactory, config)
            .build()

    }


}