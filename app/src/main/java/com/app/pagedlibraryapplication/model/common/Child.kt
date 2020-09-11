package com.app.pagedlibraryapplication.model.common

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Child : Serializable {
    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("image_url")
    @Expose
    var imageUrl: String? = null

    companion object {
        private const val serialVersionUID = -2223703166064970710L
    }
}