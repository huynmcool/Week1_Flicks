package com.nguyen.movielist.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.nguyen.movielist.R
import com.nguyen.movielist.contract.MainInterface.DetailView
import com.nguyen.movielist.databinding.ActivityDetailBinding
import com.nguyen.movielist.model.dataClass.Movie
import com.nguyen.movielist.model.dataClass.TrailersList
import com.nguyen.movielist.presenter.DetailActivityPresenter
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity(), DetailView {

    private var mPresenter : DetailActivityPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.hide()

        mPresenter = DetailActivityPresenter()
        initView()
    }

    private fun initView() {
        val movieDetail : Movie? = intent?.extras?.get("Movie") as? Movie
        movieDetail?.let { movie ->
            mPresenter?.getTrailersList(movie, fun(trailers: TrailersList?){
                val video = movie_video_detail
                lifecycle.addObserver(video)
                video.addYouTubePlayerListener(YoutubePlayerListener(fun(ytp: YouTubePlayer){
                    trailers?.youtube?.get(0)?.source.let {
                        ytp.loadVideo(it.toString(), 0f)
                    }
                }))
            })
        }
        val binding : ActivityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.movie = movieDetail
    }


    class YoutubePlayerListener(val loadVideo: (youtubePlayer: YouTubePlayer) -> Unit) : AbstractYouTubePlayerListener(){
        override fun onReady(youTubePlayer: YouTubePlayer) {
            super.onReady(youTubePlayer)
            loadVideo(youTubePlayer)
        }
    }



}
