package com.angela.lollipoptest.home

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.angela.lollipoptest.data.NewsResult
import com.angela.lollipoptest.data.source.LollipopRepository


/**
 * Paging library DataSource factory sample
 * For delivering liveData to HomeViewModel
 *
 */
class PagingDataSourceFactory(private val repository: LollipopRepository) : DataSource.Factory<String, NewsResult>() {

    private val sourceLiveData = MutableLiveData<PagingDataSource>()

    override fun create(): DataSource<String, NewsResult> {
        val source = PagingDataSource(repository)
        sourceLiveData.postValue(source)
        return source
    }
}