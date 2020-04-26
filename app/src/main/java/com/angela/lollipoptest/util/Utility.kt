package com.angela.lollipoptest.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.angela.lollipoptest.LollipopApplication


object Utility {

    fun isInternetConnected(): Boolean {
        val cm = LollipopApplication.INSTANCE
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    fun getString(resourceId: Int): String {
        return LollipopApplication.INSTANCE.getString(resourceId)
    }
}
