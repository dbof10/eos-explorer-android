package io.eos.transaction.detail.viewmodel

import android.widget.TextView
import androidx.core.text.PrecomputedTextCompat
import androidx.core.widget.TextViewCompat
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import io.eos.arch.Action
import io.eos.arch.CoroutineScopeProvider
import io.eos.arch.ViewStateStore
import io.eos.arch.emit
import io.eos.arch.produceActions
import io.eos.transaction.repository.BlockRepository
import kotlinx.coroutines.channels.ReceiveChannel

class BlockDetailViewModel(private val id: String,
                           private val gson: Gson,
                           private val repository: BlockRepository,
                           private val scopeProvider: CoroutineScopeProvider) : ViewModel() {


    val store = ViewStateStore(BlockDetailViewState(), scopeProvider)


    fun load() {
        store.dispatchActions(fetchBlockDetailAction())
    }

    private fun fetchBlockDetailAction(): ReceiveChannel<Action<BlockDetailViewState>> {

        return produceActions(scopeProvider.async) {
            emit {
                copy(loading = true)
            }

            try {
                val block = repository.getBlock(id)

                val bodyVM = BlockDetailBodyViewModel(block.id, block.producer, block.producerSignature, block.transactions.size.toString(),
                        gson.toJson(block), false, null)

                emit {
                    BlockDetailViewState(bodyVM, false, null)
                }
            } catch (e: Exception) {
                emit {
                    BlockDetailViewState(null, false, e)
                }
            }
        }
    }

    fun toggleDetail(enable: Boolean, view: TextView) {

        val state = store.state
        val body = state.content!!

        if (enable) {
            store.dispatchActions(renderBlockDetailAction(view))

        } else {
            store.dispatchAction {
                return@dispatchAction Action {
                    copy(content = body.copy(detailChecked = false), renderingText = false)
                }
            }
        }

    }

    private fun renderBlockDetailAction(view: TextView): ReceiveChannel<Action<BlockDetailViewState>> {
        val state = store.state
        val body = state.content!!

        return produceActions(scopeProvider.async) {
            emit {
                copy(renderingText = true, content = body.copy(detailChecked = true))
            }

            val precomputed = if (body.contentStringPrecomputedText == null) {
                val params = TextViewCompat.getTextMetricsParams(view)
                PrecomputedTextCompat.create(body.contentString, params)
            } else {
                body.contentStringPrecomputedText
            }
            emit {
                copy(content = body.copy(contentStringPrecomputedText = precomputed,
                        detailChecked = true),
                        renderingText = false)
            }

        }
    }

    override fun onCleared() {
        store.cancel()
        super.onCleared()
    }
}