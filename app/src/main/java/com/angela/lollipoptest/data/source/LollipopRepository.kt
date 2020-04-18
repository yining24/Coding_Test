package com.angela.lollipoptest.data.source

import com.angela.lollipoptest.data.HomeResult
import com.angela.lollipoptest.data.Result

/**
 * Interface to the LineTV layers.
 */
interface LollipopRepository {

    suspend fun getHome(): Result<HomeResult>

}
