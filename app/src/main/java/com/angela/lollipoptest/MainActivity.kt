package com.angela.lollipoptest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.angela.lollipoptest.data.News
import com.angela.lollipoptest.data.NewsResult
import com.angela.lollipoptest.data.source.LollipopRepository
import com.angela.lollipoptest.data.source.local.LollipopDatabase
import com.angela.lollipoptest.databinding.ActivityMainBinding
import com.angela.lollipoptest.home.HomeBoundaryCallback
import com.angela.lollipoptest.home.HomePagingAdapter
import com.angela.lollipoptest.home.HomeViewModel
import com.angela.lollipoptest.network.LoadApiStatus
import com.angela.lollipoptest.util.Logger

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory((applicationContext as LollipopApplication).lollipopRepository)
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = HomePagingAdapter()
        binding.recyclerHome.adapter = adapter

//        viewModel.pagingDataNews.observe(this, Observer {
//            (binding.recyclerHome.adapter as HomePagingAdapter).submitList(it)
//        })

//        viewModel.isNewsPrepared.observe(this, Observer {
//        })
//
//        viewModel.newsInLocal.observe(this, Observer {
//            Logger.i("newsInLocal from homeViewModel:: $it")
//
//        })


//        binding.recyclerHome.addOnScrollListener(object : RecyclerView.OnScrollListener(){
//
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//
//                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
//                var itemCount = linearLayoutManager?.itemCount
//                var lastPostion = linearLayoutManager?.findLastVisibleItemPosition()
//
//                if (viewModel.status.value != LoadApiStatus.LOADING) {
//                    if (linearLayoutManager != null && linearLayoutManager.findLastVisibleItemPosition() == itemCount!! - 1) {
//                        Logger.w("itemCount size = $itemCount")
//
//                        viewModel.getNews(false)
//
//                        Toast.makeText(this@MainActivity,
//                            "to the bottom!", Toast.LENGTH_SHORT).show()
//                        //bottom of list!
////                        loadMore()
////                        isLoading = true
//                    }
//                }
//            }
//
//        })

        //1
        val config = PagedList.Config.Builder()
            .setPageSize(20)
            .setEnablePlaceholders(false)
            .build()

        //2
        val liveData = initializedPagedListBuilder(config).build()

        //3
        liveData.observe(this, Observer<PagedList<News>> { pagedList ->
            adapter.submitList(pagedList)
        })


    }

    private fun initializedPagedListBuilder(config: PagedList.Config):
            LivePagedListBuilder<Int, News> {

        val database = LollipopDatabase.getInstance(this)
        val repository = LollipopApplication.INSTANCE.lollipopRepository
        val livePageListBuilder = LivePagedListBuilder<Int, News>(
            database.lollipopDatabaseDao.post(), config)

        livePageListBuilder.setBoundaryCallback(HomeBoundaryCallback(repository))

        return livePageListBuilder
    }
}
