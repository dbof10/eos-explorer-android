package io.eos.transaction.list.di

import androidx.lifecycle.ViewModel
import io.eos.arch.CoroutineScopeProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import io.eos.di.ViewModelKey
import io.eos.transaction.repository.BlockRepository
import io.eos.transaction.list.view.BlockAdapter
import io.eos.transaction.list.view.BlockFooterErrorAdapter
import io.eos.transaction.list.view.BlockFooterLoadingAdapter
import io.eos.transaction.list.view.BlockListActivity
import io.eos.transaction.list.viewmodel.BlockListViewModel

@Module
object BlockListModule {


    @Provides
    @IntoMap
    @ViewModelKey(BlockListViewModel::class)
    fun provideBlockListViewModel(activity: BlockListActivity,
                              repository: BlockRepository,
                              scopeProvider: CoroutineScopeProvider): ViewModel {

        return BlockListViewModel(activity, repository, scopeProvider)
    }

    @Provides
    fun provideBlockAdapter(): BlockAdapter {
        return BlockAdapter(BlockAdapter.DIFFER)
    }

    @Provides
    fun provideBlockFooterErrorAdapter(): BlockFooterErrorAdapter {
        return BlockFooterErrorAdapter()
    }

    @Provides
    fun provideBlockFooterLoadingAdapter(): BlockFooterLoadingAdapter {
        return BlockFooterLoadingAdapter()
    }
}