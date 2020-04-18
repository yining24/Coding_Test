package com.angela.lollipoptest.data.source.remote

import com.angela.lollipoptest.data.HomeResult
import com.angela.lollipoptest.data.source.LollipopDataSource
import com.angela.lollipoptest.network.LollipopApi
import com.angela.lollipoptest.data.*
import com.angela.lollipoptest.util.Logger


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



}
