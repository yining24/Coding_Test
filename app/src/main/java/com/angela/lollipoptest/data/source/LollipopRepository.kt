package com.angela.lollipoptest.data.source

import androidx.lifecycle.LiveData
import com.angela.lollipoptest.data.News
import com.angela.lollipoptest.data.NewsPageResult
import com.angela.lollipoptest.data.Result

/**
 * Interface to the Lollipop layers.
 */
interface LollipopRepository {

    suspend fun getNewsPage(after: String): Result<NewsPageResult>

    suspend fun insertNewsInLocal(news: List<News>)

    fun getNewsInLocal(): LiveData<List<News>>

    suspend fun deleteTable()

}
