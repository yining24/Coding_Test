package com.angela.lollipoptest.data.source.remote

import androidx.lifecycle.LiveData
import com.angela.lollipoptest.data.HomeResult
import com.angela.lollipoptest.data.source.LollipopDataSource
import com.angela.lollipoptest.network.LollipopApi
import com.angela.lollipoptest.data.*
import com.angela.lollipoptest.data.source.local.LollipopDatabase
import com.angela.lollipoptest.util.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


object LollipopRemoteDataSource : LollipopDataSource {

    override suspend fun getHome(): Result<HomeResult> {

        val getResultDeferred = LollipopApi.RETROFIT_SERVICE.getHome()
        return try {
            // this will run on a thread managed by Retrofit
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

    override suspend fun getOldHome(after: String): Result<HomeResult> {

        val getResultDeferred = LollipopApi.RETROFIT_SERVICE.getOldHome(after)
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

    override suspend fun insertNewsInLocal(news: News) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

