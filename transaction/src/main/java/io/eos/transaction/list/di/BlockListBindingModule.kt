package io.eos.transaction.list.di

import androidx.appcompat.app.AppCompatActivity
import io.eos.di.ActivityScope
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.eos.transaction.list.view.BlockListActivity

@Module(includes = [(BlockListModule::class)])
abstract class BlockListBindingModule {

    @Binds
    abstract fun provideBlockListActivity(activity: BlockListActivity): AppCompatActivity

    @Module
    abstract class Builder {
        @ActivityScope
        @ContributesAndroidInjector(modules = [BlockListBindingModule::class])
        abstract fun contributeBlockListActivity(): BlockListActivity
    }
}
