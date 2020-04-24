package com.angela.lollipoptest.home

import android.widget.Toast
import androidx.lifecycle.*
import androidx.paging.PagedList
import androidx.paging.toLiveData
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



class HomeViewModel(private val repository: LollipopRepository) : ViewModel() {

//    private val sourceFactory = PagingDataSourceFactory()
//val pagingDataNews: LiveData<PagedList<NewsResult>> = sourceFactory.toLiveData(1, null)

    val newsInLocal: LiveData<List<News>> = repository.getNewsInLocal()

    var nextPage :String = ""

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

        getNews(true)

    }


    private fun getNews(isInitial: Boolean = false) {

        coroutineScope.launch {

//            if (!Util.isInternetConnected()) {
//                Toast.makeText(
//                    LineTVApplication.INSTANCE.applicationContext,
//                    getString(R.string.internet_not_connected),
//                    Toast.LENGTH_SHORT
//                ).show()
//                if (isInitial) _status.value = LoadApiStatus.ERROR
//
//            } else {

                if (isInitial) _status.value = LoadApiStatus.LOADING
                // It will return Result object after Deferred flow

                when (val result = repository.getHome(nextPage)) {
                    is Result.Success -> {
                        _error.value = null
                        repository.deleteTable()
                        if (isInitial) _status.value = LoadApiStatus.DONE

                        nextPage = result.data.homeData.after?:""

                        val list = mutableListOf<News>()

                        result.data.homeData.children?.forEach {
                            list.add(it.news)
                        }

                        repository.insertNewsInLocal(list)

//                        result.data.homeData.children?.forEach {
//                            repository.insertNewsInLocal(it.news)
//                        }
                    }
                    is Result.Fail -> {
                        _error.value = result.error
                        if (isInitial) _status.value = LoadApiStatus.ERROR
                    }
                    is Result.Error -> {
                        _error.value = result.exception.toString()
                        if (isInitial) _status.value = LoadApiStatus.ERROR
                    }
                    else -> {
                        _error.value = getString(R.string.something_wrong)
                        if (isInitial) _status.value = LoadApiStatus.ERROR
                    }
                }
            }
            _refreshStatus.value = false

        }
    }

