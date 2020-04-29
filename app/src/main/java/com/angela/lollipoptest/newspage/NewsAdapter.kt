package com.angela.lollipoptest.newspage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.angela.lollipoptest.data.News
import com.angela.lollipoptest.databinding.ItemNewsFullBinding
import com.angela.lollipoptest.databinding.ItemNewsGridBinding


private const val ITEM_VIEW_TYPE_GRID = 1
private const val ITEM_VIEW_TYPE_FULL = 2
private const val ITEM_POSITION_FULL = 5

class NewsAdapter : ListAdapter<News, RecyclerView.ViewHolder>(DiffCallback) {

    private var newsList: MutableList<News>? = null

    class FullViewHolder(private var binding: ItemNewsFullBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: News) {
            binding.news = item
            binding.executePendingBindings()
        }
    }

    class GridViewHolder(private var binding: ItemNewsGridBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: News) {
            binding.news = item
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.title == newItem.title
                    && oldItem.time == newItem.time
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_GRID -> GridViewHolder(
                ItemNewsGridBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            ITEM_VIEW_TYPE_FULL -> FullViewHolder(
                ItemNewsFullBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val news = getItem(position)
        news?.let {
            when (holder) {
                is GridViewHolder -> {
                    holder.bind(news)
                }
                is FullViewHolder -> {
                    holder.bind(news)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % ITEM_POSITION_FULL == 0) {
            ITEM_VIEW_TYPE_FULL
        } else ITEM_VIEW_TYPE_GRID
    }

//        override fun getItemCount(): Int {
//        return newsList?.size?: 0
//    }

//    fun submitNews(news: List<News>) {
//        this.newsList = news
//        notifyDataSetChanged()
//    }
}

