package com.nguyen.movielist.model.dataClass

import com.google.gson.annotations.SerializedName


class MoviesList {
    @SerializedName("results")
    var results: ArrayList<Movie>? = null
}