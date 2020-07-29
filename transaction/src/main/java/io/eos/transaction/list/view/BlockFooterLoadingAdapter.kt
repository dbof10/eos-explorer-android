package io.eos.transaction.list.view

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.eos.transaction.R

class BlockFooterLoadingAdapter() : RecyclerView.Adapter<BlockFooterLoadingViewHolder>() {


    override fun getItemViewType(position: Int): Int {
        return R.layout.item_block_footer_loading
    }

    override fun onBindViewHolder(holder: BlockFooterLoadingViewHolder, position: Int) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlockFooterLoadingViewHolder {
        return BlockFooterLoadingViewHolder.create(parent)

    }

    override fun getItemCount(): Int {
        return 1
    }
}