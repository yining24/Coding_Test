package com.angela.lollipoptest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
import com.angela.lollipoptest.util.Logger
import com.angela.lollipoptest.util.Utility.getString

class HomeViewModel(private val repository: LollipopRepository) : ViewModel() {

    private val _newsResult = MutableLiveData<List<NewsResult>>()

    val newsResult: LiveData<List<NewsResult>>
        get() = _newsResult

    private val _homeData = MutableLiveData<HomeData>()

    val homeData: LiveData<HomeData>
        get() = _homeData


    // status: The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String>()

    val error: LiveData<String>
        get() = _error

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

        getHomeResult(true)

    }


    private fun getHomeResult(isInitial: Boolean = false) {

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

            val result = repository.getHome()

            _homeData.value = when (result) {
                    is Result.Success -> {
                        _error.value = null
                        if (isInitial) _status.value = LoadApiStatus.DONE
                        Logger.i("homeviewmodel homeItems success")
                        Logger.i("homeviewmodel homeItems :: ${result.data}")
                        Logger.i("homeviewmodel dist :: ${homeData.value?.dist}")
                        Logger.i("homeviewmodel result.data.homeData.children :: ${result.data.homeData.children}")

                        _newsResult.value = result.data.homeData.children
                        result.data.homeData
                    }
                    is Result.Fail -> {
                        _error.value = result.error
                        if (isInitial) _status.value = LoadApiStatus.ERROR
                        Logger.i("homeviewmodel homeItems fail")
                        null
                    }
                    is Result.Error -> {
                        _error.value = result.exception.toString()
                        if (isInitial) _status.value = LoadApiStatus.ERROR
                        Logger.i("homeviewmodel homeItems error")
                        null
                    }
                    else -> {
                        _error.value = getString(R.string.something_wrong)
                        if (isInitial) _status.value = LoadApiStatus.ERROR
                        Logger.i("homeviewmodel homeItems else error")
                        null
                    }
                }
            }
            _refreshStatus.value = false

        }
    }
