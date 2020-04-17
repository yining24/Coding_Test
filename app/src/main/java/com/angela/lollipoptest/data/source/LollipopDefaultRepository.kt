package com.angela.lollipoptest.data.source

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Concrete implementation to load LineTV sources.
 */
class LollipopDefaultRepository(
    private val lollipopRemoteDataSource: LollipopDataSource,
    private val lollipopLocalDataSource: LollipopDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : LollipopRepository {



}
