package com.nguyen.movielist.view.adapter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.nguyen.movielist.R
import com.nguyen.movielist.model.utils.BingdingUtils.Companion.loadText
import com.nguyen.movielist.model.dataClass.Movie
import com.nguyen.movielist.view.DetailActivity
import kotlinx.android.synthetic.main.item_movie.view.*
import kotlinx.android.synthetic.main.item_movie.view.movie_overview
import kotlinx.android.synthetic.main.item_movie.view.movie_title
import kotlinx.android.synthetic.main.item_movie_popular.view.*

class MovieListAdapter(
    private val listOfMovies: List<Movie>,
    private val context: Context,
    private var isLandscape: Boolean
) : RecyclerView.Adapter<MovieListAdapter.BaseViewHolder>() {


    fun setLandscape() {
        isLandscape = true
    }

    fun setPortrait() {
        isLandscape = false
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        var holder: BaseViewHolder? = null
        when (viewType) {
            R.layout.item_movie -> holder =
                MovieViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_movie,
                        parent,
                        false
                    )
                )
            R.layout.item_movie_popular -> holder =
                PopularMovieViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_movie_popular,
                        parent,
                        false
                    )
                )
        }
        /*holder?.let { return holder }*/
        return holder!!
    }

    override fun getItemCount(): Int {
        return listOfMovies.size
    }


    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val movie = listOfMovies[position]

        holder.bind(movie, context, isLandscape)
    }

    override fun getItemViewType(position: Int): Int {
        return if (listOfMovies[position].voteAverage!! > 5.0)
            R.layout.item_movie_popular
        else R.layout.item_movie
    }


    abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(movie: Movie, context: Context, isLandscape: Boolean)
    }


    class MovieViewHolder(itemView: View) : BaseViewHolder(itemView) {
        private val title = itemView.movie_title
        private val overview = itemView.movie_overview
        private val image = itemView.movie_poster


        override fun bind(movie: Movie, context: Context, isLandscape: Boolean) {
            title.text = movie.title
            overview.text = movie.overview
            loadImage(context, movie, isLandscape)

        }

        private fun loadImage(context: Context, movie: Movie, isLandscape: Boolean) {
            var imagePath = movie.posterPath
            if (isLandscape) imagePath = movie.backdropPath
            Glide
                .with(context)
                .load("https://image.tmdb.org/t/p/w342${imagePath}")
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                })
                .into(this.image)

            image.setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java).apply {
                    putExtra("Movie", movie)
                }
                context.startActivity(intent)
            }


        }
    }

    class PopularMovieViewHolder(itemView: View) : BaseViewHolder(itemView) {
        private val title = itemView.movie_title
        private val overview = itemView.movie_overview
        private var image = itemView.movie_image


        override fun bind(movie: Movie, context: Context, isLandscape: Boolean) {
            loadText(title, movie.title)
            loadText(overview, movie.overview)
//            title.text = movie.title
//            overview.text = movie.overview
            loadImage(context, movie)

        }

        private fun loadImage(context: Context, movie: Movie) {
            Glide
                .with(context)
                .load("https://image.tmdb.org/t/p/w342${movie.backdropPath}")
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        itemView.loading_icon_popular.visibility = View.VISIBLE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        itemView.loading_icon_popular.visibility = View.GONE
                        return false
                    }

                })
                .into(image)

            image.setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java).apply {
                    putExtra("Movie", movie)
                }
                context.startActivity(intent)
            }
        }
    }
}