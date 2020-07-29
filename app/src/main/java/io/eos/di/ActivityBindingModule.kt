package io.eos.di

import dagger.Module
import dagger.android.support.AndroidSupportInjectionModule
import io.eos.transaction.detail.di.BlockDetailBindingModule
import io.eos.transaction.list.di.BlockListBindingModule

@Module(includes = [
    AndroidSupportInjectionModule::class,
    BlockListBindingModule.Builder::class,
    BlockDetailBindingModule.Builder::class
])
abstract class ActivityBindingModule {


}