package io.eos.recyclerview

import android.view.View

class OnItemClick(private val func: ((view: View, item: Any) -> Unit)) {

    fun invoke(view: View, item: Any) {
        func.invoke(view, item)
    }
}