package io.eos.transaction.list.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.eos.recyclerview.OnItemClick
import io.eos.transaction.R

class BlockFooterErrorViewHolder private constructor(view: View) : RecyclerView.ViewHolder(view) {


    companion object {
        fun create(parent: ViewGroup): BlockFooterErrorViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_block_footer_error, parent, false)
            return BlockFooterErrorViewHolder(view)
        }
    }

    private val btRetry = view.findViewById<TextView>(R.id.btRetry)

    var onItemClick: OnItemClick? = null

    init {

        btRetry.setOnClickListener {
            onItemClick?.invoke(it, Unit)
        }
    }

}