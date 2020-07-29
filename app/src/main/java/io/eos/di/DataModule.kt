package io.eos.di

import dagger.Lazy
import dagger.Module
import dagger.Provides
import io.eos.transaction.repository.EOSApi
import io.eos.transaction.repository.BlockRepository
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
object DataModule {


    @Provides
    @Singleton
    fun provideIdiomRepository(retrofit: Retrofit): BlockRepository {
        val api = Lazy {
            retrofit.create(EOSApi::class.java)
        }
        return BlockRepository(api)
    }


}