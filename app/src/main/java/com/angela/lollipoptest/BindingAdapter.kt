package com.angela.lollipoptest

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.angela.lollipoptest.network.LoadApiStatus
import com.bumptech.glide.request.RequestOptions


/**
 * Uses the Glide library to load an image by URL into an [ImageView]
 */
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().build()
        GlideApp.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.color.transparent)
                    .error(R.color.transparent)
            )
            .into(imgView)
    }
}

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
