package com.angela.lollipoptest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.angela.lollipoptest.data.HomeItem
import com.angela.lollipoptest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

//    private val viewModel: HomeViewModel by lazy {
//        ViewModelProviders.of(this).get(HomeViewModel::class.java)
//    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.lifecycleOwner = this
//        binding.viewModel = viewModel
        val adapter = HomeAdapter()
        binding.recyclerHome.adapter = adapter


        val items = HomeItem(    //呼叫出來帶假資料
            "1",
            "111",
            "123"
        )

        adapter.submitList(listOf(items))   //帶入假資料

    }



}
