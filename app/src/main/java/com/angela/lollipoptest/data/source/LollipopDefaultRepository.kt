package com.angela.lollipoptest.data.source

import androidx.lifecycle.LiveData
import com.angela.lollipoptest.data.HomeResult
import com.angela.lollipoptest.data.News
import com.angela.lollipoptest.data.Result
import com.angela.lollipoptest.data.source.local.LollipopDatabase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Concrete implementation to load LineTV sources.
 */
class LollipopDefaultRepository(
    private val lollipopRemoteDataSource: LollipopDataSource,
    private val lollipopLocalDataSource: LollipopDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : LollipopRepository {

    override suspend fun getHome(): Result<HomeResult> {
        return lollipopRemoteDataSource.getHome()
    }

    override fun getNewsInLocal(): LiveData<List<News>> {
        return lollipopLocalDataSource.getNewsInLocal()
    }

    override suspend fun insertNewsInLocal(news: News) {
        return lollipopLocalDataSource.insertNewsInLocal(news)
    }

}
