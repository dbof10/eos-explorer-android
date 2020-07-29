package io.eos.transaction.list.viewmodel

import io.eos.transaction.model.PagingKey


data class BlockListViewState(val content: List<BlockItemViewModel>? = null,
                              val currentPage: PagingKey? = null,
                              val loading: Boolean = false,
                              val throwable: Throwable? = null,
                              val loadingMore: Boolean = false,
                              val throwableMore: Throwable? = null)