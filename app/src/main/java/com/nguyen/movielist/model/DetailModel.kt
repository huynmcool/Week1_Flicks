package com.nguyen.movielist.model

import android.util.Log
import com.nguyen.movielist.contract.MainInterface
import com.nguyen.movielist.model.MainModel.CONSTANT.MOVIE_API_KEY
import com.nguyen.movielist.model.dataClass.Movie
import com.nguyen.movielist.model.dataClass.TrailersList
import com.nguyen.movielist.model.utils.RetrofitApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailModel : MainInterface.DetailModel {


    override fun getTrailers(movie: Movie?, function: (TrailersList?) -> Unit) {
        val service = RetrofitApi.service
        val call = movie?.id?.let { service.getTrailers(it, MOVIE_API_KEY) }
        call?.enqueue(object : Callback<TrailersList> {
            override fun onFailure(call: Call<TrailersList>, t: Throwable) {
                Log.d("MainActivity", "Fail ${t.message}")
            }

            override fun onResponse(call: Call<TrailersList>, response: Response<TrailersList>) {
                Log.d("MainActivity", "Success loading trailer ${movie.title}")
                if(response.body()?.youtube?.size == 0) return
                function.invoke(response.body())
            }
        })
    }

}