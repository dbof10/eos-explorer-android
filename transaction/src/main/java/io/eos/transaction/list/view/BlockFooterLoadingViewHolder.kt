package io.eos.transaction.list.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.eos.transaction.R


class BlockFooterLoadingViewHolder private constructor(view: View) : RecyclerView.ViewHolder(view) {


    companion object {
        fun create(parent: ViewGroup): BlockFooterLoadingViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_block_footer_loading, parent, false)
            return BlockFooterLoadingViewHolder(view)
        }
    }


}