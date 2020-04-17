package com.angela.lollipoptest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.angela.lollipoptest.data.HomeItem
import com.angela.lollipoptest.databinding.ItemHomeFullBinding
import com.angela.lollipoptest.databinding.ItemHomeGridBinding


private const val ITEM_VIEW_TYPE_GRID = 1
private const val ITEM_VIEW_TYPE_FULL = 2

class HomeAdapter : ListAdapter<HomeItem, RecyclerView.ViewHolder>(DiffCallback) {

    class FullViewHolder(private var binding: ItemHomeFullBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HomeItem) {

            binding.homeItem = item
            binding.executePendingBindings()
        }
    }

    class GridViewHolder(private var binding: ItemHomeGridBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HomeItem) {
            binding.homeItem = item
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<HomeItem>() {
        override fun areItemsTheSame(oldItem: HomeItem, newItem: HomeItem): Boolean {
            return oldItem === newItem
        }
        override fun areContentsTheSame(oldItem: HomeItem, newItem: HomeItem): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_GRID -> GridViewHolder(ItemHomeGridBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false))
            ITEM_VIEW_TYPE_FULL -> FullViewHolder(ItemHomeFullBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false))
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is GridViewHolder -> {
                holder.bind(getItem(position) as HomeItem)
            }
            is FullViewHolder -> {
                holder.bind(getItem(position) as HomeItem)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % 5 == 0) {
            ITEM_VIEW_TYPE_FULL
        } else ITEM_VIEW_TYPE_GRID
    }
}
