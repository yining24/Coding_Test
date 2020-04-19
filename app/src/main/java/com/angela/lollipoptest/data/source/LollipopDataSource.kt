package com.angela.lollipoptest.data.source

import androidx.lifecycle.LiveData
import com.angela.lollipoptest.data.HomeResult
import com.angela.lollipoptest.data.News
import com.angela.lollipoptest.data.Result

/**
 * Main entry point for accessing LineTV sources.
 */
interface LollipopDataSource {

    suspend fun getHome(): Result<HomeResult>

    suspend fun insertNewsInLocal(news: News)

    fun getNewsInLocal(): LiveData<List<News>>
}
