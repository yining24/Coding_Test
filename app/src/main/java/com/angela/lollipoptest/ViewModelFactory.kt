package com.angela.lollipoptest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.angela.lollipoptest.data.source.LollipopRepository
import com.angela.lollipoptest.home.HomeViewModel


/**
 * Factory for all ViewModels with repository.
 */
@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(
    private val lollipopRepository: LollipopRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(HomeViewModel::class.java) ->
                    HomeViewModel(lollipopRepository)

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}