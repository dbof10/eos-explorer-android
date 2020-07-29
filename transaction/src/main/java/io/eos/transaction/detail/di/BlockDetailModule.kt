package io.eos.transaction.detail.di

import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import io.eos.arch.CoroutineScopeProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import io.eos.di.ViewModelKey
import io.eos.transaction.detail.view.BlockDetailActivity
import io.eos.transaction.detail.viewmodel.BlockDetailViewModel
import io.eos.transaction.repository.BlockRepository

@Module
object BlockDetailModule {


    @Provides
    @IntoMap
    @ViewModelKey(BlockDetailViewModel::class)
    fun provideBlockDetailViewModel(activity: BlockDetailActivity,
                                    gson: Gson,
                                    idiomRepository: BlockRepository,
                                    scopeProvider: CoroutineScopeProvider): ViewModel {

        val id = activity.intent.getStringExtra(BlockDetailActivity.KEY_ID)!!
        return BlockDetailViewModel(id,gson, idiomRepository, scopeProvider)
    }

}