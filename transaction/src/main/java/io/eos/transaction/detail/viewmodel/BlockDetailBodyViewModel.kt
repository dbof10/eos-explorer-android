package io.eos.transaction.detail.viewmodel

import android.view.View
import androidx.core.text.PrecomputedTextCompat


data class BlockDetailBodyViewModel(val id: String,
                                    val producer: String,
                                    val producerSignature: String,
                                    val transactionsCount: String,
                                    val contentString: String,
                                    val detailChecked: Boolean,
                                    val contentStringPrecomputedText: PrecomputedTextCompat?) {
    val detailVisibility: Int
        get() {
            return if (detailChecked) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
}