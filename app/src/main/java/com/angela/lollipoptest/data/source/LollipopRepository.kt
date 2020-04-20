package com.angela.lollipoptest.data.source

import androidx.lifecycle.LiveData
import com.angela.lollipoptest.data.HomeResult
import com.angela.lollipoptest.data.News
import com.angela.lollipoptest.data.Result

/**
 * Interface to the LineTV layers.
 */
interface LollipopRepository {

    suspend fun getHome(): Result<HomeResult>

    suspend fun getOldHome(after: String): Result<HomeResult>

    suspend fun insertNewsInLocal(news: News)

    fun getNewsInLocal(): LiveData<List<News>>

}
