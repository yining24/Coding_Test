package com.angela.lollipoptest.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.angela.lollipoptest.data.News
import com.angela.lollipoptest.databinding.ItemHomeFullBinding
import com.angela.lollipoptest.databinding.ItemHomeGridBinding


private const val ITEM_VIEW_TYPE_GRID = 1
private const val ITEM_VIEW_TYPE_FULL = 2
private const val ITEM_POSITION_FULL = 5

class HomePagingAdapter : ListAdapter<News, RecyclerView.ViewHolder>(DiffCallback) {

//    private var newsList: List<News>? = null

    class FullViewHolder(private var binding: ItemHomeFullBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: News) {

            binding.news = item
            binding.executePendingBindings()
        }
    }

    class GridViewHolder(private var binding: ItemHomeGridBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: News) {
            binding.news = item
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.id == newItem.id
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
                    holder.bind(getItem(position))
                }
                is FullViewHolder -> {
                    holder.bind(getItem(position))
                }
            }
    }

//    override fun getItemCount(): Int {
//        return newsList?.size ?: 0
//    }

//    fun submitNews(news: List<News>) {
//        this.newsList = news
//        notifyDataSetChanged()
//    }

    override fun getItemViewType(position: Int): Int {
        return if (position % ITEM_POSITION_FULL == 0) {
            ITEM_VIEW_TYPE_FULL
        } else ITEM_VIEW_TYPE_GRID
    }
}

