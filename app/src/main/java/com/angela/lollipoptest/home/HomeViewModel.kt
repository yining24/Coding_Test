package com.angela.lollipoptest.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angela.lollipoptest.data.source.LollipopRepository
import com.angela.lollipoptest.network.LoadApiStatus
import com.angela.lollipoptest.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class HomeViewModel(private val repository: LollipopRepository) : ViewModel() {

    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

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

    fun showLoading() {
        _status.value = LoadApiStatus.LOADING
    }

    fun finishLoading() {
        _status.value = LoadApiStatus.DONE
    }
}

