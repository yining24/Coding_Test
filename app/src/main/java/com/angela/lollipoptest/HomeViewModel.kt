package com.angela.lollipoptest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angela.lollipoptest.data.HomeItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import com.angela.lollipoptest.data.source.LollipopRepository
import com.angela.lollipoptest.network.LoadApiStatus

class HomeViewModel(private val repository: LollipopRepository) : ViewModel() {

    private val _homeItems = MutableLiveData<List<HomeItem>>()

    val homeItems: LiveData<List<HomeItem>>
        get() = _homeItems

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

    }


}