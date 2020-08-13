package com.nguyen.movielist.model.utils

import com.nguyen.movielist.model.MainModel
import com.nguyen.movielist.model.intf.MovieApiInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitApi {
    companion object{
        val service: MovieApiInterface = Retrofit.Builder()
            .baseUrl(MainModel.MOVIE_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApiInterface::class.java)
    }
}