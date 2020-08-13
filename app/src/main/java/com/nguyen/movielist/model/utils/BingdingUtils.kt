package com.nguyen.movielist.model.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter

class BingdingUtils {

    companion object{
        @BindingAdapter("text")
        fun loadText(view: TextView?, value: String?) {
            view?.text = value
        }
    }

}