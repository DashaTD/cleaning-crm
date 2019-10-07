package com.ronasit.fiesta.di.modules

import com.google.gson.GsonBuilder
import com.ronasit.fiesta.BuildConfig
import com.ronasit.fiesta.network.AuthInterceptor
import com.ronasit.fiesta.network.FiestaApi
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
object NetworkModule {

    lateinit var fiestaApi: FiestaApi

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideFiestaApi(retrofit: Retrofit): FiestaApi {
        fiestaApi = retrofit.create(FiestaApi::class.java)
        return fiestaApi
    }

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRetrofitInterface(authInterceptor: AuthInterceptor): Retrofit {
        val gson = GsonBuilder()
            .setPrettyPrinting()
            .excludeFieldsWithoutExposeAnnotation()
            .enableComplexMapKeySerialization()
            .create()

        val logsInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logsInterceptor)
            .addInterceptor(authInterceptor)
            .authenticator(authInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(client)
            .build()
    }
}