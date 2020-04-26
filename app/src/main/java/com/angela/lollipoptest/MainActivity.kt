package com.angela.lollipoptest

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.angela.lollipoptest.data.News
import com.angela.lollipoptest.data.source.local.LollipopDatabase
import com.angela.lollipoptest.databinding.ActivityMainBinding
import com.angela.lollipoptest.home.HomeBoundaryCallback
import com.angela.lollipoptest.home.HomePagingAdapter
import com.angela.lollipoptest.home.HomeViewModel
import com.angela.lollipoptest.util.Logger
import com.angela.lollipoptest.util.Utility


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

        val config = PagedList.Config.Builder()
            .setPageSize(50)
            .setPrefetchDistance(1)
            .setEnablePlaceholders(false)
            .build()

        val newsLiveData = initializedPagedListBuilder(config).build()

        newsLiveData.observe(this, Observer<PagedList<News>> { pagedList ->
            adapter.submitList(pagedList)
        })

        viewModel.status.observe(this, Observer {
            Logger.w("status change $it")
        })


    }

    override fun onStart() {
        super.onStart()
        if (!Utility.isInternetConnected()) {
            Toast.makeText(
                this,
                "請開啟網路連線", Toast.LENGTH_SHORT
            ).show()
            Logger.d("no network")
        } else {
            viewModel.deleteTable()
        }
    }

    private fun initializedPagedListBuilder(config: PagedList.Config):
            LivePagedListBuilder<Int, News> {

        val database = LollipopDatabase.getInstance(this)
        val repository = LollipopApplication.INSTANCE.lollipopRepository
        val livePageListBuilder = LivePagedListBuilder<Int, News>(
            database.lollipopDatabaseDao.post(), config
        )

        livePageListBuilder.setBoundaryCallback(HomeBoundaryCallback(repository, viewModel))

        return livePageListBuilder
    }
}

