package com.nguyen.movielist.model

import android.util.Log
import com.nguyen.movielist.contract.MainInterface
import com.nguyen.movielist.model.dataClass.Movie
import com.nguyen.movielist.model.dataClass.MoviesList
import com.nguyen.movielist.model.utils.RetrofitApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainModel : MainInterface.Model {

    companion object CONSTANT{
        const val MOVIE_API_URL = "https://api.themoviedb.org/"
        const val MOVIE_API_KEY = "b49e91bf4051bca0a41222e065cdb663"
    }


    private var listOfMovies : ArrayList<Movie>? = null
    private var mIsLoadingMore : Boolean = false
    private var currentPage = 1
    private val mService = RetrofitApi.service


    override fun getMovieList(function: (List<Movie>?) -> Unit) {
        val call =  mService.getMovies(MOVIE_API_KEY, currentPage)
        call.enqueue(object : Callback<MoviesList> {
            override fun onFailure(call: Call<MoviesList>, t: Throwable) {
                Log.d("MainActivity", "Fail ${t.message}")
            }

            override fun onResponse(call: Call<MoviesList>, response: Response<MoviesList>) {
                Log.d("MainActivity", "Success loading movie list")
                listOfMovies = response.body()?.results
                function.invoke(listOfMovies)
            }
        })
    }

    override fun loadMore(function: (result: Boolean) -> Unit) {
        if(mIsLoadingMore) {
            return
        }

        mIsLoadingMore = true
        currentPage++
        val call =  mService.getMovies(MOVIE_API_KEY, currentPage)
        call.enqueue(object : Callback<MoviesList> {
            override fun onFailure(call: Call<MoviesList>, t: Throwable) {
                Log.d("MainActivity", "Fail ${t.message}")
                function.invoke(false)
            }
            override fun onResponse(call: Call<MoviesList>, response: Response<MoviesList>) {
                for(movie in response.body()?.results!!){
                    listOfMovies?.add(movie)
                }
                mIsLoadingMore = false
                Log.d("LoadMore", "Finish loading more movies! $currentPage")
                function.invoke(true)
            }
        })
    }






}




