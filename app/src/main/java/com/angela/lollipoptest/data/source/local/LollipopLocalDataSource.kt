package com.angela.lollipoptest.data.source.local

import android.content.Context
import androidx.lifecycle.LiveData
import com.angela.lollipoptest.data.HomeResult
import com.angela.lollipoptest.data.News
import com.angela.lollipoptest.data.NewsResult
import com.angela.lollipoptest.data.Result
import com.angela.lollipoptest.data.source.LollipopDataSource
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class LollipopLocalDataSource(val context: Context) : LollipopDataSource {

    override suspend fun getHome(after: String): Result<HomeResult>{
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getNewsInLocal(): LiveData<List<News>> {
        return LollipopDatabase.getInstance(context).lollipopDatabaseDao.getAllNews()
    }

    override suspend fun insertNewsInLocal(news: List<News>) {
        withContext(Dispatchers.IO) {
            LollipopDatabase.getInstance(context).lollipopDatabaseDao.insert(news)
        }
    }
    override suspend fun postNewsInLocal() {
        withContext(Dispatchers.IO) {
            LollipopDatabase.getInstance(context).lollipopDatabaseDao.post()
        }
    }

    override suspend fun deleteTable() {
        withContext(Dispatchers.IO) {
            LollipopDatabase.getInstance(context).lollipopDatabaseDao.deleteTable()
        }
    }

}
