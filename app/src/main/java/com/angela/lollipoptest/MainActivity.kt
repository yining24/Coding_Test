package com.angela.lollipoptest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.angela.lollipoptest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory((applicationContext as LollipopApplication).lollipopRepository) }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        val adapter = HomeAdapter()
        binding.recyclerHome.adapter = adapter


//        val items = HomeItem(    //呼叫出來帶假資料
//            "1",
//            "111",
//            "123"
//        )
//
//        adapter.submitList(listOf(items))   //帶入假資料

    }



}
