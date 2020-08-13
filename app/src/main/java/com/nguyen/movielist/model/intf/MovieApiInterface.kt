package com.nguyen.movielist.model.intf

import com.nguyen.movielist.model.dataClass.MoviesList
import com.nguyen.movielist.model.dataClass.TrailersList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiInterface {


    @GET("3/movie/now_playing?")
    fun getMovies(
        @Query("api_key") key: String,
        @Query("page") page: Int
    ) : Call<MoviesList>




    @GET("3/movie/{id}/trailers?")
    fun getTrailers(
        @Path("id") id: Int,
        @Query("api_key") key: String
    ) : Call<TrailersList>

}