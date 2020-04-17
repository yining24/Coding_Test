package com.angela.lollipoptest

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.angela.lollipoptest.network.LoadApiStatus


/**
 * Uses the Glide library to load an image by URL into an [ImageView]
 */
//@BindingAdapter("imageUrl")
//fun bindImage(imgView: ImageView, imgUrl: String?) {
//    imgUrl?.let {
//        val imgUri = it.toUri().buildUpon().build()
//        GlideApp.with(imgView.context)
//            .load(imgUri)
//            .apply(
//                RequestOptions()
//                    .placeholder(R.drawable.loading)
//                    .error(R.drawable.loading)
//            )
//            .into(imgView)
//    }
//}

/**
 * According to [LoadApiStatus] to decide the visibility of [ProgressBar]
 */
@BindingAdapter("setupApiStatus")
fun bindApiStatus(view: ProgressBar, status: LoadApiStatus?) {
    when (status) {
        LoadApiStatus.LOADING -> view.visibility = View.VISIBLE
        LoadApiStatus.DONE, LoadApiStatus.ERROR -> view.visibility = View.GONE
    }
}

/**
 * According to [message] to decide the visibility of [ProgressBar]
 */
@BindingAdapter("setupApiErrorMessage")
fun bindApiErrorMessage(view: TextView, message: String?) {
    when (message) {
        null, "" -> {
            view.visibility = View.GONE
        }
        else -> {
            view.text = message
            view.visibility = View.VISIBLE
        }
    }
}