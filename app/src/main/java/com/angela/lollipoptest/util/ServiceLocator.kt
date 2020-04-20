package com.angela.lollipoptest.util

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.angela.lollipoptest.data.source.LollipopDataSource
import com.angela.lollipoptest.data.source.LollipopDefaultRepository
import com.angela.lollipoptest.data.source.LollipopRepository
import com.angela.lollipoptest.data.source.local.LollipopLocalDataSource
import com.angela.lollipoptest.data.source.remote.LollipopRemoteDataSource

object ServiceLocator {

    @Volatile
    var lollipopRepository: LollipopRepository? = null
        @VisibleForTesting set

    fun provideTasksRepository(context: Context): LollipopRepository {
        synchronized(this) {
            return lollipopRepository
                ?: lollipopRepository
                ?: createLollipopRepository(context)
        }
    }

    private fun createLollipopRepository(context: Context): LollipopRepository {
        return LollipopDefaultRepository(
            LollipopRemoteDataSource,
            createLocalDataSource(context)
        )
    }

    private fun createLocalDataSource(context: Context): LollipopDataSource {
        return LollipopLocalDataSource(context)
    }
}