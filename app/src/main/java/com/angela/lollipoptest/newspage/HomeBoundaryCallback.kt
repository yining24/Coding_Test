//package com.angela.lollipoptest.newspage
//
//import androidx.paging.PagedList
//import com.angela.lollipoptest.data.News
//import com.angela.lollipoptest.data.Result
//import com.angela.lollipoptest.data.source.LollipopRepository
//import com.angela.lollipoptest.util.Logger
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.Job
//import kotlinx.coroutines.launch
//
//
//class HomeBoundaryCallback(
//    private val repository: LollipopRepository,
//    private val viewModel: NewsViewModel
//) : PagedList.BoundaryCallback<News>() {
//
//    private val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
//    private var nextPage: String? = ""
//
//    override fun onZeroItemsLoaded() {
//        super.onZeroItemsLoaded()
//        coroutineScope.launch {
//            Logger.w("onZeroItemsLoading")
//            viewModel.showLoading()
//
//            when (val result = repository.getNewsPage(nextPage ?: "")) {
//                is Result.Success -> {
//                    Logger.w("onZeroItemsLoaded success")
//                    viewModel.finishLoading()
//
//                    nextPage = result.data.newsPage.after
//
//                    val newsList = result.data.newsPage.newsPageList?.map { it.news }
//                    repository.insertNewsInLocal(newsList ?: listOf())
//                }
//                is Result.Fail -> {
//                    Logger.i("onZeroItemsLoaded fail")
//                }
//                is Result.Error -> {
//                    Logger.i("onZeroItemsLoaded error")
//                }
//                else -> {
//                    Logger.i("onZeroItemsLoaded else")
//                }
//            }
//        }
//    }
//
//    override fun onItemAtEndLoaded(itemAtEnd: News) {
//        super.onItemAtEndLoaded(itemAtEnd)
//        coroutineScope.launch {
//            Logger.w("onItemAtEndLoading")
//            viewModel.showLoading()
//
//            when (val result = repository.getNewsPage(nextPage ?: "")) {
//                is Result.Success -> {
//                    Logger.w("onItemAtEndLoaded success")
//                    viewModel.finishLoading()
//
//                    nextPage = result.data.newsPage.after
//
//                    val newsList = result.data.newsPage.newsPageList?.map { it.news }
//                    repository.insertNewsInLocal(newsList ?: listOf())
//                }
//                is Result.Fail -> {
//                    Logger.i("onZeroItemsLoaded fail")
//                }
//                is Result.Error -> {
//                    Logger.i("onZeroItemsLoaded error")
//                }
//                else -> {
//                    Logger.i("onZeroItemsLoaded else")
//                }
//            }
//        }
//
//    }
//}
