package io.eos.transaction.list.viewmodel

import android.app.Activity
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import io.eos.arch.Action
import io.eos.arch.CoroutineScopeProvider
import io.eos.arch.ViewStateStore
import io.eos.arch.emit
import io.eos.arch.produceActions
import io.eos.transaction.detail.view.BlockDetailActivity
import io.eos.transaction.repository.BlockRepository
import io.eos.transaction.model.Block
import io.eos.transaction.model.PagingKey
import kotlinx.coroutines.channels.ReceiveChannel

class BlockListViewModel(private val activity: Activity,
                         private val repository: BlockRepository,
                         private val scopeProvider: CoroutineScopeProvider) : ViewModel() {


    val store = ViewStateStore(BlockListViewState(), scopeProvider)


    companion object {
        private const val LIMIT = 20
        private const val LIMIT_MORE = 10

        @VisibleForTesting
        internal fun toViewModel(block: Block): BlockItemViewModel {
            return BlockItemViewModel(block.id, "Block number #${block.blockNum}")
        }

    }

    private fun fetchBlocksAction(): ReceiveChannel<Action<BlockListViewState>> {

        return produceActions(scopeProvider.async) {
            emit {
                copy(loading = true)
            }

            try {
                val blocks = repository.fetch(LIMIT)

                val vms = blocks
                        .map {
                            toViewModel(it)
                        }
                val last = blocks.last()

                emit {
                    BlockListViewState(vms, PagingKey(last.id, last.previous), false, null)
                }
            } catch (e: Exception) {
                emit {
                    BlockListViewState(null, null, false, e)
                }
            }

        }
    }



    fun loadMore() {
        store.dispatchActions(fetchMoreBlocksAction())
    }

    private fun fetchMoreBlocksAction(): ReceiveChannel<Action<BlockListViewState>> {

        val state = store.state
        val nextPage = state.currentPage!!.prevKey

        return produceActions(scopeProvider.async) {
            emit {
                copy(loadingMore = true)
            }

            try {
                val blocks = repository.fetchNext(LIMIT_MORE, nextPage)
                val vms = blocks
                        .map {
                            toViewModel(it)
                        }

                val last = blocks.last()
                val current = state.content!!
                val total = current + vms
                emit {
                    BlockListViewState(total, PagingKey(last.id, last.previous), false, null, false, null)
                }
            } catch (e: Exception) {
                emit {
                    BlockListViewState(state.content, state.currentPage, false, null, false, e)
                }
            }
        }
    }


    fun onClicked(item: Any) {
        if (item is BlockItemViewModel) {
            val intent = BlockDetailActivity.makeIntent(activity, item.id)
            activity.startActivity(intent)
        }
    }

    fun load() {
        store.dispatchActions(fetchBlocksAction())
    }

    override fun onCleared() {
        store.cancel()
        super.onCleared()

    }

}