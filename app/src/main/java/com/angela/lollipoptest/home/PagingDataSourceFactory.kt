package com.angela.lollipoptest.home

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.angela.lollipoptest.data.NewsResult

class PagingDataSourceFactory : DataSource.Factory<String, NewsResult>() {

    val sourceLiveData = MutableLiveData<PagingDataSource>()

    override fun create(): DataSource<String, NewsResult> {
        val source = PagingDataSource()
        sourceLiveData.postValue(source)
        return source
    }
}