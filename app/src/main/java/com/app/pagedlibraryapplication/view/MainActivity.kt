package com.app.pagedlibraryapplication.view


import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.LinearLayoutManager
import com.app.pagedlibraryapplication.R
import com.app.pagedlibraryapplication.adapter.StoryAdapter
import com.app.pagedlibraryapplication.viewmodel.StoryViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val storyAdapter = StoryAdapter()

        recyclerView.layoutManager = LinearLayoutManager(this)

        val storyViewModel = ViewModelProvider(this).get(StoryViewModel::class.java)

        storyViewModel.storyPagedList.observe(this, Observer {
            storyAdapter.submitList(it)
        })

        recyclerView.adapter = storyAdapter

    }
}