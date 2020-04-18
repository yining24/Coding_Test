package com.angela.lollipoptest.data.source.local

import android.content.Context
import com.angela.lollipoptest.data.HomeResult
import com.angela.lollipoptest.data.Result
import com.angela.lollipoptest.data.source.LollipopDataSource

/**
 * Concrete implementation of a LineTV source as a db.
 */
class LollipopLocalDataSource(val context: Context) : LollipopDataSource {

    override suspend fun getHome(): Result<HomeResult> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
