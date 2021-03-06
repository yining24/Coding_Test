package com.angela.lollipoptest.data.source

import androidx.lifecycle.LiveData
import com.angela.lollipoptest.data.News
import com.angela.lollipoptest.data.NewsPageResult
import com.angela.lollipoptest.data.Result

/**
 * Concrete implementation to load Lollipop sources.
 */
class LollipopDefaultRepository(
    private val lollipopRemoteDataSource: LollipopDataSource,
    private val lollipopLocalDataSource: LollipopDataSource
) : LollipopRepository {
    override suspend fun getNewsPage(after: String): Result<NewsPageResult> {
        return lollipopRemoteDataSource.getNewsPage(after)
    }

    override fun getNewsInLocal(): LiveData<List<News>> {
        return lollipopLocalDataSource.getNewsInLocal()
    }

    override suspend fun insertNewsInLocal(news: List<News>) {
        return lollipopLocalDataSource.insertNewsInLocal(news)
    }

    override suspend fun deleteTable() {
        return lollipopLocalDataSource.deleteTable()
    }

}
