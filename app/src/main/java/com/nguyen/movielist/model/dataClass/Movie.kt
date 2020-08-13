package com.nguyen.movielist.model.dataClass

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Movie : Serializable {

    @SerializedName("poster_path")
    var posterPath : String? = null

    @SerializedName("backdrop_path")
    var backdropPath : String? = null

    @SerializedName("release_date")
    var releaseDate : String? = null

    var id : Int? = null
    var title : String? = null
    @SerializedName("vote_average")
    var voteAverage : Float? = null
    var overview : String? = null
}