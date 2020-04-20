package com.angela.lollipoptest.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.angela.lollipoptest.data.News
import com.angela.lollipoptest.data.NewsResult
import com.angela.lollipoptest.databinding.ItemHomeFullBinding
import com.angela.lollipoptest.databinding.ItemHomeGridBinding


private const val ITEM_VIEW_TYPE_GRID = 1
private const val ITEM_VIEW_TYPE_FULL = 2

class HomePagingAdapter : PagedListAdapter<NewsResult, RecyclerView.ViewHolder>(DiffCallback) {

    class FullViewHolder(private var binding: ItemHomeFullBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NewsResult) {

            binding.news = item.news
            binding.executePendingBindings()
        }
    }

    class GridViewHolder(private var binding: ItemHomeGridBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NewsResult) {
            binding.news = item.news
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<NewsResult>() {
        override fun areItemsTheSame(oldItem: NewsResult, newItem: NewsResult): Boolean {
            return oldItem === newItem
        }
        override fun areContentsTheSame(oldItem: NewsResult, newItem: NewsResult): Boolean {
            return oldItem.news.id == newItem.news.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_GRID -> GridViewHolder(
                ItemHomeGridBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            ITEM_VIEW_TYPE_FULL -> FullViewHolder(
                ItemHomeFullBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is GridViewHolder -> {
                holder.bind(getItem(position) as NewsResult)
            }
            is FullViewHolder -> {
                holder.bind(getItem(position) as NewsResult)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % 5 == 0) {
            ITEM_VIEW_TYPE_FULL
        } else ITEM_VIEW_TYPE_GRID
    }
}

