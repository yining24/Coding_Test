package com.angela.lollipoptest.data.source

import androidx.lifecycle.LiveData
import com.angela.lollipoptest.data.HomeResult
import com.angela.lollipoptest.data.News
import com.angela.lollipoptest.data.Result

/**
 * Interface to the Lollipop layers.
 */
interface LollipopRepository {

    suspend fun getHome(after: String): Result<HomeResult>

    suspend fun insertNewsInLocal(news: List<News>)

    fun getNewsInLocal(): LiveData<List<News>>

    suspend fun deleteTable()

}
