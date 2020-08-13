package com.nguyen.movielist.presenter


import com.nguyen.movielist.contract.MainInterface.DetailPresenter
import com.nguyen.movielist.model.DetailModel
import com.nguyen.movielist.model.dataClass.Movie
import com.nguyen.movielist.model.dataClass.TrailersList


class DetailActivityPresenter: DetailPresenter {
    private val model: DetailModel = DetailModel()
    private var movie : Movie? = null
    private var trailers: TrailersList? = null

    override fun getMovie(): Movie? {
        return movie
    }

    override fun getTrailersList(movie: Movie?, function: (TrailersList?) -> Unit) {
        model.getTrailers(movie) {
            trailers = it
            function.invoke((it))
        }
    }


}