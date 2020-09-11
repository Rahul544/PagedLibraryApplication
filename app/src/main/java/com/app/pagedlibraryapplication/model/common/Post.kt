package com.app.pagedlibraryapplication.model.common

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Post : Serializable {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("like_counts")
    @Expose
    var likeCounts: Int? = null

    @SerializedName("comment_counts")
    @Expose
    var commentCounts: Int? = null

    @SerializedName("content")
    @Expose
    var content: String? = null

    @SerializedName("child_name")
    @Expose
    var childName: String? = null

    @SerializedName("post_type")
    @Expose
    var postType: String? = null

    @SerializedName("default_post_type")
    @Expose
    var defaultPostType: String? = null

    @SerializedName("privacy_type")
    @Expose
    var privacyType: String? = null

    @SerializedName("default_privacy_type")
    @Expose
    var defaultPrivacyType: String? = null

    @SerializedName("attachment_count")
    @Expose
    var attachmentCount: Int? = null

    @SerializedName("media_url")
    @Expose
    var mediaUrl: String? = null

    @SerializedName("you_like")
    @Expose
    var youLike: Boolean? = null

    @SerializedName("date")
    @Expose
    var date: String? = null

    @SerializedName("dummy_post")
    @Expose
    var dummyPost: Boolean? = null

}