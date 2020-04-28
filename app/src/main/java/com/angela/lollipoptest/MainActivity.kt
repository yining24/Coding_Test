package com.angela.lollipoptest

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.angela.lollipoptest.databinding.ActivityMainBinding
import com.angela.lollipoptest.network.LoadApiStatus
import com.angela.lollipoptest.newspage.HomeViewModel
import com.angela.lollipoptest.newspage.NewsAdapter
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

        val adapter = NewsAdapter()
        binding.recyclerHome.adapter = adapter


        binding.recyclerHome.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = linearLayoutManager.childCount
                val pastVisiblePostion = linearLayoutManager.findFirstCompletelyVisibleItemPosition()
                val total = adapter.itemCount


                if (viewModel.status.value != LoadApiStatus.LOADING) {
                    if ( visibleItemCount + pastVisiblePostion >= total) {
                        Logger.w("total itemCount size = $total")

                        viewModel.getNews(false)
                        adapter.notifyDataSetChanged()
                    }
                }
            }

        })

        viewModel.newsInLocal.observe(this, Observer {
            adapter.submitList(it)

            Logger.w("viewModel.newsInLocal.observe =====$it")
        })



        viewModel.status.observe(this, Observer {
            Logger.w("status change $it")
        })




    }

//    override fun onStart() {
//        super.onStart()
//        if (!Utility.isInternetConnected()) {
//            Toast.makeText(
//                this,
//                "請開啟網路連線", Toast.LENGTH_SHORT
//            ).show()
//            Logger.d("no network")
//        } else {
//            viewModel.getNews(true)
//        }
//    }


//    private fun initializedPagedListBuilder(config: PagedList.Config):
//            LivePagedListBuilder<Int, News> {
//
//        val database = LollipopDatabase.getInstance(this)
//        val repository = LollipopApplication.INSTANCE.lollipopRepository
//        val livePageListBuilder = LivePagedListBuilder<Int, News>(
//            database.lollipopDatabaseDao.post(), config
//        )
//
//        livePageListBuilder.setBoundaryCallback(HomeBoundaryCallback(repository, viewModel))
//
//        return livePageListBuilder
//    }
}

