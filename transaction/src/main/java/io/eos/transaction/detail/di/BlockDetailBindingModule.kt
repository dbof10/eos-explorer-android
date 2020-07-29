package io.eos.transaction.detail.di

import androidx.appcompat.app.AppCompatActivity
import io.eos.di.ActivityScope
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.eos.transaction.detail.view.BlockDetailActivity

@Module(includes = [(BlockDetailModule::class)])
abstract class BlockDetailBindingModule {

    @Binds
    abstract fun provideBlockDetailActivity(activity: BlockDetailActivity): AppCompatActivity

    @Module
    abstract class Builder {
        @ActivityScope
        @ContributesAndroidInjector(modules = [BlockDetailBindingModule::class])
        abstract fun contributeBlockDetailActivity(): BlockDetailActivity
    }
}
