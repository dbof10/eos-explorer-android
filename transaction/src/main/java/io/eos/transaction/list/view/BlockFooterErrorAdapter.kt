package io.eos.transaction.list.view

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.eos.recyclerview.OnItemClick
import io.eos.transaction.R


class BlockFooterErrorAdapter() : RecyclerView.Adapter<BlockFooterErrorViewHolder>() {

    var listener: OnItemClick? = null

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_block_footer_error
    }

    override fun onBindViewHolder(holder: BlockFooterErrorViewHolder, position: Int) {
        holder.onItemClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlockFooterErrorViewHolder {
        return BlockFooterErrorViewHolder.create(parent)

    }

    override fun getItemCount(): Int {
        return 1
    }
}