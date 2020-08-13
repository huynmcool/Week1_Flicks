package com.nguyen.movielist.view

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nguyen.movielist.R
import com.nguyen.movielist.contract.MainInterface.MainView
import com.nguyen.movielist.presenter.MainActivityPresenter
import com.nguyen.movielist.view.adapter.MovieListAdapter
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity(), MainView {

    private var mPresenter: MainActivityPresenter? = null
    private var mIsLandscape : Boolean = false
    private var mAdapter : MovieListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mPresenter = MainActivityPresenter()
        initView()
    }

    private fun initView() {
        mPresenter?.getMovieList { listOfMovies ->
            mAdapter = MovieListAdapter(
                mPresenter?.listOfMovies!!,
                this,
                mIsLandscape
            )
            movie_list_recyclerview.adapter = mAdapter
            movie_list_recyclerview.layoutManager = LinearLayoutManager(this)
            movie_list_recyclerview.addOnScrollListener(ScrollListener(fun(recyclerView : RecyclerView){
                if (!recyclerView.canScrollVertically(1)) {
                    mPresenter!!.loadMore{
                        if(it) movie_list_recyclerview.adapter!!.notifyItemInserted(listOfMovies!!.size - 1)
                    }
                }
            }))
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mAdapter?.setLandscape()
            mAdapter?.notifyDataSetChanged()
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            mAdapter?.setPortrait()
            mAdapter?.notifyDataSetChanged()
        }
    }

    class ScrollListener(val loadMore : (recyclerView: RecyclerView) -> Unit) : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            loadMore(recyclerView)
        }

    }


}
