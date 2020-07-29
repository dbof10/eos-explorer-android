package io.eos.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.eos.arch.AppCoroutineScopeProvider
import io.eos.arch.CoroutineScopeProvider
import javax.inject.Provider
import javax.inject.Singleton


@Module(includes = [NetworkModule::class])
object AppModule {


    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
                .setPrettyPrinting()
                .create()
    }

    @Provides
    fun provideViewModelFactory(
            providers: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
    ): ViewModelProvider.Factory {
        return AppViewModelFactory(providers)
    }

    @Provides
    @Singleton
    fun provideCoroutineScopeProvider(): CoroutineScopeProvider = AppCoroutineScopeProvider()

}