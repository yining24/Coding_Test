package com.angela.lollipoptest.data.source

import androidx.lifecycle.LiveData
import com.angela.lollipoptest.data.HomeResult
import com.angela.lollipoptest.data.News
import com.angela.lollipoptest.data.Result

/**
 * Main entry point for accessing Lollipop sources.
 */
interface LollipopDataSource {

    suspend fun getHome(after: String): Result<HomeResult>

    suspend fun insertNewsInLocal(news: News)

    fun getNewsInLocal(): LiveData<List<News>>

}
