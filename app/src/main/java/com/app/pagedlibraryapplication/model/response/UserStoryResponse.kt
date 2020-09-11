package com.app.pagedlibraryapplication.model.response

import com.app.pagedlibraryapplication.model.common.Child
import com.app.pagedlibraryapplication.model.common.Post

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class UserStoryResponse : Serializable {
    @SerializedName("posts")
    @Expose
    var posts: List<Post>? = null

    @SerializedName("child")
    @Expose
    var child: Child? = null

    @SerializedName("next_page")
    @Expose
    var nextPage: Int? = null

}