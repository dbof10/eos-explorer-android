package io.eos.transaction.list.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.eos.recyclerview.OnItemClick
import io.eos.transaction.list.viewmodel.BlockItemViewModel
import io.eos.transaction.R

class BlockItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {


    private lateinit var viewModel: BlockItemViewModel

    private val tvBlock = view.findViewById<TextView>(R.id.tvBlock)
    var onItemClick: OnItemClick? = null

    companion object {
        fun create(parent: ViewGroup): BlockItemViewHolder {

            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_block, parent, false)
            return BlockItemViewHolder(view)
        }
    }

    init {
        view.setOnClickListener {
            onItemClick?.invoke(it, viewModel)
        }
    }

    fun bind(item: BlockItemViewModel) {
        this.viewModel = item

        tvBlock.text = item.numberDisplay
    }
}