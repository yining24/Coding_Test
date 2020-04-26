package com.angela.lollipoptest.home

import androidx.paging.PagedList
import com.angela.lollipoptest.LollipopApplication
import com.angela.lollipoptest.data.News
import com.angela.lollipoptest.data.NewsResult
import com.angela.lollipoptest.data.source.LollipopRepository
import com.angela.lollipoptest.data.source.local.LollipopDatabaseDao
import com.angela.lollipoptest.network.LollipopApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import com.angela.lollipoptest.data.Result
import com.angela.lollipoptest.data.source.local.LollipopDatabase
import com.angela.lollipoptest.network.LoadApiStatus
import com.angela.lollipoptest.util.Logger
import retrofit2.Retrofit
import java.util.concurrent.Executors


class HomeBoundaryCallback(
    private val repository: LollipopRepository

) : PagedList.BoundaryCallback<News>() {


    private val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    private var nextPage: String? = null

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        coroutineScope.launch {

            Logger.w("onZeroItemsLoaded ing")


            when (val result = repository.getHome(nextPage ?: "")) {
                is Result.Success -> {
                    Logger.w("onZeroItemsLoaded success")

                    nextPage = result.data.homeData.after

                    val news = result.data.homeData.children?.map { it.news }

                    repository.insertNewsInLocal(news ?: listOf())

//                    result.data.homeData.children?.forEach {
//                        repository.insertNewsInLocal(it.news)
//                    }
                }
                is Result.Fail -> {
                    Logger.i("onZeroItemsLoaded fail")
                }
                is Result.Error -> {
                    Logger.i("onZeroItemsLoaded error")
                }
                else -> {
                    Logger.i("onZeroItemsLoaded else")
                }
            }
        }
    }

        override fun onItemAtEndLoaded(itemAtEnd: News) {
            super.onItemAtEndLoaded(itemAtEnd)


            coroutineScope.launch {

                Logger.w("onItemAtEndLoaded ing")

                when (val result = repository.getHome(nextPage ?: "")) {
                    is Result.Success -> {
                        Logger.w("onItemAtEndLoaded success")

                        nextPage = result.data.homeData.after

                        val news = result.data.homeData.children?.map { it.news }

                        repository.insertNewsInLocal(news ?: listOf())
                    }
                    is Result.Fail -> {
                        Logger.i("onZeroItemsLoaded fail")
                    }
                    is Result.Error -> {
                        Logger.i("onZeroItemsLoaded error")
                    }
                    else -> {
                        Logger.i("onZeroItemsLoaded else")
                    }
                }
            }

            }
        }

//        private fun updateNewsInLocal(newsResult : List<NewsResult>) {
//            newsResult.forEach {
//                repository.insertNewsInLocal(news = it.news)
//            }
//        }
