//package com.angela.lollipoptest.newspage
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.paging.PageKeyedDataSource
//import com.angela.lollipoptest.R
//import com.angela.lollipoptest.data.NewsResult
//import com.angela.lollipoptest.data.Result
//import com.angela.lollipoptest.data.source.LollipopRepository
//import com.angela.lollipoptest.network.LoadApiStatus
//import com.angela.lollipoptest.util.Utility.getString
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.Job
//import kotlinx.coroutines.launch
//
//
///**
// * Paging library callback with network sample
// *
// */
//class PagingDataSource(private val repository: LollipopRepository) :
//    PageKeyedDataSource<String, NewsResult>() {
//
//    private val _statusInitialLoad = MutableLiveData<LoadApiStatus>()
//
//    val statusInitialLoad: LiveData<LoadApiStatus>
//        get() = _statusInitialLoad
//
//    private val _errorInitialLoad = MutableLiveData<String>()
//
//    val errorInitialLoad: LiveData<String>
//        get() = _errorInitialLoad
//
//    private val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
//
//
//    override fun loadInitial(
//        params: LoadInitialParams<String>,
//        callback: LoadInitialCallback<String, NewsResult>
//    ) {
//
//        coroutineScope.launch {
//
//            _statusInitialLoad.value = LoadApiStatus.LOADING
//
//            when (val result = repository.getNewsPage("")) {
//                is Result.Success -> {
//                    _errorInitialLoad.value = null
//                    _statusInitialLoad.value = LoadApiStatus.DONE
//                    result.data.newsPage.newsPageList?.let {
//                        callback.onResult(it, null, result.data.newsPage.after)
//                    }
//                }
//                is Result.Fail -> {
//                    _errorInitialLoad.value = result.error
//                    _statusInitialLoad.value = LoadApiStatus.ERROR
//                }
//                is Result.Error -> {
//                    _errorInitialLoad.value = result.exception.toString()
//                    _statusInitialLoad.value = LoadApiStatus.ERROR
//                }
//                else -> {
//                    _errorInitialLoad.value = getString(R.string.something_wrong)
//                    _statusInitialLoad.value = LoadApiStatus.ERROR
//                }
//            }
//        }
//    }
//
//    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, NewsResult>) {
//
//        coroutineScope.launch {
//            _statusInitialLoad.value = LoadApiStatus.LOADING
//
//            when (val result = repository.getNewsPage(params.key)) {
//                is Result.Success -> {
//                    _statusInitialLoad.value = LoadApiStatus.DONE
//                    result.data.newsPage.newsPageList?.let {
//                        callback.onResult(it, result.data.newsPage.after)
//                    }
//                }
//            }
//        }
//    }
//
//    override fun loadBefore(
//        params: LoadParams<String>,
//        callback: LoadCallback<String, NewsResult>
//    ) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//}