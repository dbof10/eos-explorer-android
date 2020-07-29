package io.eos.transaction.list.view

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import io.eos.recyclerview.OnItemClick
import io.eos.transaction.R
import io.eos.transaction.list.viewmodel.BlockItemViewModel

class BlockAdapter(diffResult: DiffUtil.ItemCallback<BlockItemViewModel>) :
        ListAdapter<BlockItemViewModel, BlockItemViewHolder>(diffResult) {

    var listener: OnItemClick? = null


    companion object {

        val DIFFER = object : DiffUtil.ItemCallback<BlockItemViewModel>() {
            override fun areItemsTheSame(oldItem: BlockItemViewModel, newItem: BlockItemViewModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: BlockItemViewModel, newItem: BlockItemViewModel): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlockItemViewHolder {
        return BlockItemViewHolder.create(parent)
    }


    override fun getItemViewType(position: Int): Int {
        return R.layout.item_block
    }

    override fun onBindViewHolder(holder: BlockItemViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
            holder.onItemClick = listener
        }
    }


}