package com.app.pagedlibraryapplication.factory

import android.content.ClipData.Item
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.app.pagedlibraryapplication.model.common.Post
import com.app.pagedlibraryapplication.source.StoryDataSource


class StoryDataSourceFactory : DataSource.Factory<Int,Post>() {
    //creating the mutable live data


    val storyLiveDataSource = MutableLiveData<StoryDataSource>()

    override fun create(): DataSource<Int, Post> {
        val storyDataSource = StoryDataSource()
        storyLiveDataSource.postValue(storyDataSource)
        return storyDataSource
    }

}