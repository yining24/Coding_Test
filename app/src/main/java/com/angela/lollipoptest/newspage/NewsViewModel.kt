package com.angela.lollipoptest.newspage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angela.lollipoptest.data.News
import com.angela.lollipoptest.data.Result
import com.angela.lollipoptest.data.source.LollipopRepository
import com.angela.lollipoptest.network.LoadApiStatus
import com.angela.lollipoptest.util.Logger
import com.angela.lollipoptest.util.Utility
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class NewsViewModel(private val repository: LollipopRepository) : ViewModel() {

    val newsInLocal = repository.getNewsInLocal()

    var nextPage: String = ""

    val isInternetConnected = MutableLiveData<Boolean>()

    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

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

    fun getNews(isInitial: Boolean = false) {

        if (!Utility.isInternetConnected()) {
            _status.value = LoadApiStatus.ERROR
            isInternetConnected.value = false
        } else {
            coroutineScope.launch {

                if (isInitial) {
                    deleteTable()
                    nextPage = ""
                }
                if (refreshStatus.value != true) _status.value = LoadApiStatus.LOADING

                when (val result = repository.getNewsPage(nextPage)) {
                    is Result.Success -> {
                        _status.value = LoadApiStatus.DONE

                        nextPage = result.data.newsPage.after ?: ""

                        val list = mutableListOf<News>()
                        result.data.newsPage.newsPageList?.forEach {
                            list.add(it.news)
                        }
                        repository.insertNewsInLocal(list)
                    }
                    is Result.Fail -> {
                        _status.value = LoadApiStatus.ERROR
                    }
                    is Result.Error -> {
                        _status.value = LoadApiStatus.ERROR
                    }
                    else -> {
                        _status.value = LoadApiStatus.ERROR
                    }
                }
                _refreshStatus.value = false
            }
        }
    }

    private fun deleteTable() {
        coroutineScope.launch {
            repository.deleteTable()
        }
    }

    fun refresh() {
        if (status.value != LoadApiStatus.LOADING) {
            _refreshStatus.value = true
            getNews(true)
        }
    }
}

