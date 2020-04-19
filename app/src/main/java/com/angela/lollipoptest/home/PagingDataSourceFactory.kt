package com.angela.lollipoptest.home

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.angela.lollipoptest.data.NewsResult

class PagingDataSourceFactory : DataSource.Factory<Int, NewsResult>() {

    val sourceLiveData = MutableLiveData<PagingDataSource>()

    override fun create(): DataSource<Int, NewsResult> {
        val source = PagingDataSource()
        sourceLiveData.postValue(source)
        return source
    }
}