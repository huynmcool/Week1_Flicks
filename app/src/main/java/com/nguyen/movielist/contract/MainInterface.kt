package com.nguyen.movielist.contract

import com.nguyen.movielist.model.dataClass.Movie
import com.nguyen.movielist.model.dataClass.TrailersList

interface MainInterface {

    interface MainView

    interface MainPresenter {
        fun getMovieList(function : (List<Movie>?) -> Unit)
        fun loadMore(function: (result: Boolean) -> Unit)
    }

    interface Model {
        fun getMovieList(function : (List<Movie>?) -> Unit)
        fun loadMore(function: (result: Boolean) -> Unit)
    }

    interface DetailView

    interface DetailPresenter {
        fun getMovie() : Movie?
        fun getTrailersList(movie : Movie?, function: (TrailersList?) -> Unit)
    }

    interface DetailModel {
        fun getTrailers(movie: Movie?, function: (TrailersList?) -> Unit)
    }

}