package com.angela.lollipoptest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.angela.lollipoptest.databinding.ActivityMainBinding
import com.angela.lollipoptest.home.HomePagingAdapter
import com.angela.lollipoptest.home.HomeViewModel

class MainActivity : AppCompatActivity() {


    val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory((applicationContext as LollipopApplication).lollipopRepository) }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        val adapter = HomePagingAdapter()
        binding.recyclerHome.adapter = adapter

        viewModel.pagingDataNews.observe(this, Observer {
            (binding.recyclerHome.adapter as HomePagingAdapter).submitList(it)
        })



//        val items = HomeItem(    //呼叫出來帶假資料
//            "1",
//            "111",
//            "123"
//        )
//
//        adapter.submitList(listOf(items))   //帶入假資料

    }



}
