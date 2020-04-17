package com.angela.lollipoptest.util

import android.util.Log
import com.angela.lollipoptest.BuildConfig

object Logger {

    private const val TAG = "Angelaa"

    fun d(content: String) { if (BuildConfig.LOGGER_VISIABLE) Log.d(TAG, content) }
    fun i(content: String) { if (BuildConfig.LOGGER_VISIABLE) Log.i(TAG, content) }
    fun w(content: String) { if (BuildConfig.LOGGER_VISIABLE) Log.w(TAG, content) }

}