package com.angela.lollipoptest.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.angela.lollipoptest.LollipopApplication
import com.angela.lollipoptest.R
import com.angela.lollipoptest.data.NewsResult
import com.angela.lollipoptest.data.Result
import com.angela.lollipoptest.network.LoadApiStatus
import com.angela.lollipoptest.util.Utility.getString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PagingDataSource : PageKeyedDataSource<String, NewsResult>() {

//    val newsInLocal: LiveData<List<News>> = LollipopApplication.INSTANCE.lollipopRepository.getNewsInLocal()

    private val _statusInitialLoad = MutableLiveData<LoadApiStatus>()

    val statusInitialLoad: LiveData<LoadApiStatus>
        get() = _statusInitialLoad

    private val _errorInitialLoad = MutableLiveData<String>()

    val errorInitialLoad: LiveData<String>
        get() = _errorInitialLoad

    private val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)


    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, NewsResult>) {

        coroutineScope.launch {

            _statusInitialLoad.value = LoadApiStatus.LOADING

            val result = LollipopApplication.INSTANCE.lollipopRepository.getHome("")
            when (result) {
                is Result.Success -> {
                    _errorInitialLoad.value = null
                    _statusInitialLoad.value = LoadApiStatus.DONE
                    result.data.homeData.children?.forEach {
                        LollipopApplication.INSTANCE.lollipopRepository.insertNewsInLocal(it.news)
//                        Logger.w("insertNewsInLocal(it.news):: ${it.news}")
                    }
                    result.data.homeData.children?.let {
                        callback.onResult(it, null, result.data.homeData.after) }
                }
                is Result.Fail -> {
                    _errorInitialLoad.value = result.error
                    _statusInitialLoad.value = LoadApiStatus.ERROR
                }
                is Result.Error -> {
                    _errorInitialLoad.value = result.exception.toString()
                    _statusInitialLoad.value = LoadApiStatus.ERROR
                }
                else -> {
                    _errorInitialLoad.value = getString(R.string.something_wrong)
                    _statusInitialLoad.value = LoadApiStatus.ERROR
                }
            }
        }
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, NewsResult>) {

        coroutineScope.launch {
            _statusInitialLoad.value = LoadApiStatus.LOADING

            val result = LollipopApplication.INSTANCE.lollipopRepository.getHome(params.key)
            when (result) {
                is Result.Success -> {
                    _statusInitialLoad.value = LoadApiStatus.DONE
                    result.data.homeData.children?.let {
                        callback.onResult(it, result.data.homeData.after) }
                }
            }
        }
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, NewsResult>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}