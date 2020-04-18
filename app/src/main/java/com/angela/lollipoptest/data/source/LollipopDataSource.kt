package com.angela.lollipoptest.data.source

import com.angela.lollipoptest.data.HomeResult
import com.angela.lollipoptest.data.Result

/**
 * Main entry point for accessing LineTV sources.
 */
interface LollipopDataSource {

    suspend fun getHome(): Result<HomeResult>

}
