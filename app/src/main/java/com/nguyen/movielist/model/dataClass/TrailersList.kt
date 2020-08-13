package com.nguyen.movielist.model.dataClass

import com.google.gson.annotations.SerializedName

class TrailersList {
    @SerializedName("youtube")
    var youtube: List<Trailer>? = null
}