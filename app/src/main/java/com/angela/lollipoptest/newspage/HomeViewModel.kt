package com.angela.lollipoptest.newspage

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angela.lollipoptest.LollipopApplication
import com.angela.lollipoptest.R
import com.angela.lollipoptest.data.News
import com.angela.lollipoptest.data.source.LollipopRepository
import com.angela.lollipoptest.network.LoadApiStatus
import com.angela.lollipoptest.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.angela.lollipoptest.data.Result
import com.angela.lollipoptest.util.Utility
import com.angela.lollipoptest.util.Utility.getString


class HomeViewModel(private val repository: LollipopRepository) : ViewModel() {

    val newsInLocal = repository.getNewsInLocal()

    var nextPage : String = ""

    val isInternetConnected = Utility.isInternetConnected()




    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

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

    fun getNews(isInitial: Boolean = false) {

        if (!Utility.isInternetConnected()) Logger.d("connect false") else Logger.i("connect true")

        coroutineScope.launch {

            if (!Utility.isInternetConnected()) {
                Toast.makeText(
                    LollipopApplication.INSTANCE.applicationContext,
                    getString(R.string.internet_not_connected),
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                if (isInitial) deleteTable()

                _status.value = LoadApiStatus.LOADING

                when (val result = repository.getNewsPage(nextPage)) {
                    is Result.Success -> {

                        _error.value = null
                        _status.value = LoadApiStatus.DONE

                        nextPage = result.data.newsPage.after ?: ""

                        val list = mutableListOf<News>()
                        result.data.newsPage.newsPageList?.forEach {
                            list.add(it.news)
                        }
                        repository.insertNewsInLocal(list)

//                        result.data.homeData.children?.forEach {
//                            repository.insertNewsInLocal(it.news)
//                        }
                    }
                    is Result.Fail -> {
                        _error.value = result.error
                        _status.value = LoadApiStatus.ERROR
                    }
                    is Result.Error -> {
                        _error.value = result.exception.toString()
                        _status.value = LoadApiStatus.ERROR
                    }
                    else -> {
                        _error.value = getString(R.string.something_wrong)
                        _status.value = LoadApiStatus.ERROR
                    }
                }
            }
            _refreshStatus.value = false
        }
    }


    fun deleteTable() {
        coroutineScope.launch {
            Logger.w("deleteTable")
            repository.deleteTable()
        }
    }

    fun showLoading() {
        _status.value = LoadApiStatus.LOADING
    }

    fun finishLoading() {
        _status.value = LoadApiStatus.DONE
    }
}

