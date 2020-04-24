package com.angela.lollipoptest.home
//
//import androidx.paging.PagedList
//import com.angela.lollipoptest.data.News
//import com.angela.lollipoptest.data.NewsResult
//import com.angela.lollipoptest.data.source.LollipopRepository
//import com.angela.lollipoptest.data.source.local.LollipopDatabaseDao
//import com.angela.lollipoptest.network.LollipopApiService
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.Job
//import kotlinx.coroutines.launch
//import okhttp3.OkHttpClient
//import okhttp3.Request
//import com.angela.lollipoptest.data.Result
//import com.angela.lollipoptest.util.Logger
//
//
//class HomeBoundaryCallback(val repository: LollipopRepository) : PagedList.BoundaryCallback<NewsResult>() {
//
////    private val remoteDataSource: LollipopApiService by inject()
////    private val localDataSource: LollipopDatabaseDao by inject()
////    private val httpClient: OkHttpClient
//
//    private val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
//
//    private var nextPage: String? = null
//
//    override fun onZeroItemsLoaded() {
//        super.onZeroItemsLoaded()
//        coroutineScope.launch {
//
//
//            when (val response = repository.getHome(nextPage ?: "")) {
//                is Result.Success -> {
//                    Logger.w("onZeroItemsLoaded success")
//
//                    nextPage = response.data.homeData.after
//
//                    response.data.homeData.children?.forEach {
//                        repository.insertNewsInLocal(news = it.news)
//                    }
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
//        override fun onItemAtEndLoaded(itemAtEnd: NewsResult) {
//            super.onItemAtEndLoaded(itemAtEnd)
//
//            coroutineScope.launch {
//                when (val response = repository.getHome(nextPage ?: "")) {
//                    is Result.Success -> {
//                        Logger.w("onItemAtEndLoaded success")
//
//                        nextPage = response.data.homeData.after
//
//                        response.data.homeData.children?.forEach {
//                            repository.insertNewsInLocal(news = it.news)
//                        }
//                    }
//                    is Result.Fail -> {
//                        Logger.i("onZeroItemsLoaded fail")
//                    }
//                    is Result.Error -> {
//                        Logger.i("onZeroItemsLoaded error")
//                    }
//                    else -> {
//                        Logger.i("onZeroItemsLoaded else")
//                    }
//                }
//            }
//
//
//                if (response.isSuccessful) {
//                    nextPageUrl = parseNextPageUrl(response.headers())
//                    val listType = object : TypeToken<List<Post>>() {}.type
//                    val postList: List<Post> = gson.fromJson(response.body()?.string(), listType)
//                    upsertPostList(postList)
//                }
//            }
//        }
//
//        private fun updateNewsInLocal(newsResult : List<NewsResult>) {
//            newsResult.forEach {
//                repository.insertNewsInLocal(news = it.news)
//            }
//        }
//    }