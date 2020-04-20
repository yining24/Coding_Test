package com.angela.lollipoptest.data.source

import androidx.lifecycle.LiveData
import com.angela.lollipoptest.data.HomeResult
import com.angela.lollipoptest.data.News
import com.angela.lollipoptest.data.Result

/**
 * Concrete implementation to load Lollipop sources.
 */
class LollipopDefaultRepository(
    private val lollipopRemoteDataSource: LollipopDataSource,
    private val lollipopLocalDataSource: LollipopDataSource
) : LollipopRepository {

    override suspend fun getHome(after: String): Result<HomeResult>{
        return lollipopRemoteDataSource.getHome(after)
    }

    override fun getNewsInLocal(): LiveData<List<News>> {
        return lollipopLocalDataSource.getNewsInLocal()
    }

    override suspend fun insertNewsInLocal(news: News) {
        return lollipopLocalDataSource.insertNewsInLocal(news)
    }

}
