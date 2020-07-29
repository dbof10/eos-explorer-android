package io.eos.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import io.eos.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Singleton

@Module
object NetworkModule {


    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    Timber.tag("OkHttp").d(message)
                }

            })

            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            builder.addInterceptor(loggingInterceptor)
        }
        return builder
                .followRedirects(false)
                .build()
    }


    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson,
                        okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
                .baseUrl("https://eos.greymass.com/v1/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }
}