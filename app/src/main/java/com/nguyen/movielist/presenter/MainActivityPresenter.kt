package com.nguyen.movielist.presenter

import com.nguyen.movielist.contract.MainInterface.*
import com.nguyen.movielist.model.MainModel
import com.nguyen.movielist.model.dataClass.Movie

class MainActivityPresenter: MainPresenter{
    private val model: Model = MainModel()
    var listOfMovies : List<Movie>? = null

    override fun getMovieList(function: (List<Movie>?) -> Unit) {
        model.getMovieList {
            listOfMovies = it
            function.invoke(listOfMovies)
        }
    }

    override fun loadMore(function: (result: Boolean) -> Unit) {
        model.loadMore{
            function.invoke(it)
        }
    }

}