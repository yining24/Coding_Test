package com.angela.lollipoptest.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.angela.lollipoptest.data.HomeData
import com.angela.lollipoptest.data.News
import com.angela.lollipoptest.data.NewsResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import com.angela.lollipoptest.data.source.LollipopRepository
import com.angela.lollipoptest.network.LoadApiStatus
import kotlinx.coroutines.launch
import com.angela.lollipoptest.data.Result
import com.angela.lollipoptest.home.PagingDataSource
import com.angela.lollipoptest.home.PagingDataSourceFactory
import com.angela.lollipoptest.util.Logger
import com.angela.lollipoptest.util.Utility.getString

class HomeViewModel(private val repository: LollipopRepository) : ViewModel() {

//    val videosLocal = LivePagedListBuilder(voiceTubeRepository.getVideoByDatabase(),3).build()

    private val sourceFactory = PagingDataSourceFactory()

    val pagingDataNews: LiveData<PagedList<NewsResult>> = sourceFactory.toLiveData(1, null)


    // Handle load api status
    val status: LiveData<LoadApiStatus> = Transformations.switchMap(sourceFactory.sourceLiveData) {
        it.statusInitialLoad
    }

    val error: LiveData<String> = Transformations.switchMap(sourceFactory.sourceLiveData) {
        it.errorInitialLoad
    }


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
    }
