package com.app.pagedlibraryapplication.view


import android.os.Bundle
import android.os.Handler
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.LinearLayoutManager
import com.app.pagedlibraryapplication.R
import com.app.pagedlibraryapplication.adapter.StoryAdapter
import com.app.pagedlibraryapplication.utills.NetworkState
import com.app.pagedlibraryapplication.viewmodel.StoryViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var storyViewModel: StoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val storyAdapter = StoryAdapter()

        recyclerView.layoutManager = LinearLayoutManager(this)
        initialProgressBar.visibility = View.VISIBLE

        swipeRefresh.visibility = View.GONE

        storyViewModel = ViewModelProvider(this).get(StoryViewModel::class.java)

        storyViewModel.storyPagedList.observe(this, Observer {
            swipeRefresh.visibility = View.VISIBLE
            swipeRefresh.isRefreshing = false
            val handler = Handler()
            handler.postDelayed(Runnable {
                initialProgressBar.visibility = View.GONE
            }, 1500)
            storyAdapter.submitList(it)
        })

        storyViewModel.networkState.observe(this, Observer {
            storyAdapter.setNetworkState(it)
        })

        recyclerView.adapter = storyAdapter

        initSwipeToRefresh()

    }

    private fun initSwipeToRefresh() {
        storyViewModel.initialState.observe(this, Observer {
            swipeRefresh.isRefreshing = it == NetworkState.LOADING
        })

        swipeRefresh.setOnRefreshListener {
            storyViewModel.refresh()
        }
    }
}