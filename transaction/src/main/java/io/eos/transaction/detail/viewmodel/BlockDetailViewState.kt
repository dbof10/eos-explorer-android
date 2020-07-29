package io.eos.transaction.detail.viewmodel

data class BlockDetailViewState(val content: BlockDetailBodyViewModel? = null,
                                val loading: Boolean = false,
                                val throwable: Throwable? = null,
                                val renderingText: Boolean = false)