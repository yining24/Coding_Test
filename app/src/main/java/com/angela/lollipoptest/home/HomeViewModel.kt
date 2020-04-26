package com.angela.lollipoptest.home

import android.app.Activity
import android.app.Application
import android.app.ApplicationErrorReport
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import androidx.paging.PagedList
import com.angela.lollipoptest.R
import com.angela.lollipoptest.data.News
import com.angela.lollipoptest.data.NewsResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import com.angela.lollipoptest.data.source.LollipopRepository
import com.angela.lollipoptest.network.LoadApiStatus
import com.angela.lollipoptest.util.Logger
import kotlinx.coroutines.launch
import com.angela.lollipoptest.data.Result
import com.angela.lollipoptest.util.Utility.getString
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.annotation.NonNull
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import com.angela.lollipoptest.LollipopApplication
import com.angela.lollipoptest.data.source.local.LollipopDatabase
import com.angela.lollipoptest.util.Utility


class HomeViewModel(private val repository: LollipopRepository, pagingRepository: PagingRepository) : ViewModel() {

//    private val sourceFactory = PagingDataSourceFactory()
//val pagingDataNews: LiveData<PagedList<NewsResult>> = sourceFactory.toLiveData(1, null)

    val pagedListLiveData = pagingRepository.getDataItem(LollipopApplication.INSTANCE)

    var nextPage :String = ""
//
//
//    val isNewsPrepared = MediatorLiveData<Boolean>().apply {
//        addSource(newsIn) {
//            value = !newsIn.value.isNullOrEmpty()
//            if (value == true) {
//                _newsInLocal.value = newsIn.value
//            }
//        }
//    }
    private val _newsInLocal = MutableLiveData<List<News>>()
    val newsInLocal: LiveData<List<News>>
        get() = _newsInLocal


//
//    // Handle load api status
//    val status: LiveData<LoadApiStatus> = Transformations.switchMap(sourceFactory.sourceLiveData) {
//        it.statusInitialLoad
//    }
//
//    val error: LiveData<String> = Transformations.switchMap(sourceFactory.sourceLiveData) {
//        it.errorInitialLoad
//    }

    // status: The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    // error: The internal MutableLiveData that stores the error of the most recent request
    private val _error = MutableLiveData<String>()

    val error: LiveData<String>
        get() = _error

    // status for the loading icon of swl
    private val _refreshStatus = MutableLiveData<Boolean>()

    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus


    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {
        Logger.i("------------------------------------")
        Logger.i("[${this::class.simpleName}]${this}")
        Logger.i("------------------------------------")


    }

    fun deleteTable() {

        coroutineScope.launch {
                Logger.w("deleteTable")
                repository.deleteTable()
            }
        }

    }


class PagingRepository : PagingRepositoryCallback {

    private lateinit var localDataSource: DataSource.Factory<Int, News>

    override fun getDataItem(application: Application): LiveData<PagedList<News>> {

        val pagedListLiveData: LiveData<PagedList<News>> by lazy {

            localDataSource = LollipopDatabase.getInstance(application).lollipopDatabaseDao.post()

            val config = PagedList.Config.Builder()
                .setPageSize(25)
                .setEnablePlaceholders(false)
                .build()

            LivePagedListBuilder(localDataSource, config)
                .setBoundaryCallback(HomeBoundaryCallback(LollipopApplication.INSTANCE.lollipopRepository))
                .build()
        }

        return pagedListLiveData
    }
}

interface PagingRepositoryCallback {
    fun getDataItem(application: Application): LiveData<PagedList<News>>
}