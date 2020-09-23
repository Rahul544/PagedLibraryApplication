package com.app.pagedlibraryapplication.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.pagedlibraryapplication.R
import com.app.pagedlibraryapplication.model.common.Post
import com.app.pagedlibraryapplication.utills.NetworkState
import com.app.pagedlibraryapplication.viewholders.NetworkStateItemViewHolder
import com.app.pagedlibraryapplication.viewholders.StoryViewHolder
import kotlinx.android.synthetic.main.story_list_item.view.*

class StoryAdapter : PagedListAdapter<Post,RecyclerView.ViewHolder>(POST_COMPARATOR) {

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            R.layout.story_list_item -> {
                StoryViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.story_list_item, parent, false))
            }
            R.layout.network_state_item -> {
                NetworkStateItemViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.network_state_item,parent,false))
            }
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }

//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.story_list_item, parent, false)
//        return StoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val post = getItem(position)
        when (getItemViewType(position)) {
            R.layout.network_state_item ->{
                (holder as NetworkStateItemViewHolder).bindTo(
                    networkState)
            }
            R.layout.story_list_item -> {
                post?.let {
                    (holder as StoryViewHolder).bind(it)
                }
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.network_state_item
        } else {
            R.layout.story_list_item
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

    fun setNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

}