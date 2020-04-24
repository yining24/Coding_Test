package com.angela.lollipoptest.data.source.remote

import androidx.lifecycle.LiveData
import com.angela.lollipoptest.data.HomeResult
import com.angela.lollipoptest.data.source.LollipopDataSource
import com.angela.lollipoptest.network.LollipopApi
import com.angela.lollipoptest.data.*
import com.angela.lollipoptest.util.Logger

object LollipopRemoteDataSource : LollipopDataSource {

    override suspend fun getHome(after: String): Result<HomeResult> {

        val getResultDeferred = LollipopApi.retrofitService.getHome(after)
        return try {
            val listResult = getResultDeferred.await()

            listResult.error?.let {
                return Result.Fail(it)
            }
            Result.Success(listResult)

        } catch (e: Exception) {
            Logger.w("[${this::class.simpleName}] exception=${e.message}")
            Result.Error(e)
        }
    }

    override fun getNewsInLocal(): LiveData<List<News>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun insertNewsInLocal(news: List<News>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun deleteTable() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

