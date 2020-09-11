package com.app.pagedlibraryapplication.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.pagedlibraryapplication.R
import com.app.pagedlibraryapplication.model.common.Post
import kotlinx.android.synthetic.main.story_list_item.view.*

class StoryAdapter : PagedListAdapter<Post,StoryAdapter.StoryViewHolder>(POST_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.story_list_item, parent, false)
        return StoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val post = getItem(position)
        post?.let {
            holder.bind(it)
        }
    }


    class StoryViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val postName = view.name
        val postContent = view.content

        fun bind(it: Post) {
            postName.text = it.childName
            postContent.text = it.content
        }

    }

    companion object {
        private val POST_COMPARATOR = object : DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean =
                oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
                newItem.equals(oldItem)
        }
    }


}