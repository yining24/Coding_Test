package com.angela.lollipoptest.home

import androidx.paging.PagedList
import com.angela.lollipoptest.data.News
import com.angela.lollipoptest.data.source.LollipopRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.angela.lollipoptest.data.Result
import com.angela.lollipoptest.util.Logger


class HomeBoundaryCallback(
    private val repository: LollipopRepository

) : PagedList.BoundaryCallback<News>() {


    private val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    private var nextPage: String? = null

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        coroutineScope.launch {

            Logger.w("onZeroItemsLoaded ing")


            when (val result = repository.getNewsPage(nextPage ?: "")) {
                is Result.Success -> {
                    Logger.w("onZeroItemsLoaded success")

                    nextPage = result.data.newsPage.after

                    val news = result.data.newsPage.newsPageList?.map { it.news }

                    repository.insertNewsInLocal(news ?: listOf())

//                    result.data.newsPage.newsPageList?.forEach {
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

                when (val result = repository.getNewsPage(nextPage ?: "")) {
                    is Result.Success -> {
                        Logger.w("onItemAtEndLoaded success")

                        nextPage = result.data.newsPage.after

                        val news = result.data.newsPage.newsPageList?.map { it.news }

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
