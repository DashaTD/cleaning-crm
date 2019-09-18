package com.ronasit.fiesta.di.modules

import com.google.gson.GsonBuilder
import com.ronasit.fiesta.BuildConfig
import com.ronasit.fiesta.network.FiestaApi
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
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
    internal fun provideRetrofitInterface(): Retrofit {
        val gson = GsonBuilder()
            .setPrettyPrinting()
            .excludeFieldsWithoutExposeAnnotation()
            .enableComplexMapKeySerialization()
            .create()

        val logsInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        val headerInterceptor = Interceptor { chain ->
            val builder = chain
                .request()
                .newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader(
                    "Authorization",
                    "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vZGV2LmFwaS5maWVzdGEucm9uYXNpdC5jb20vYXBpL2F1dGhvcml6ZSIsImlhdCI6MTU2ODc5ODI0MSwiZXhwIjoxNTY4ODAxODQxLCJuYmYiOjE1Njg3OTgyNDEsImp0aSI6IlZNYUE2dlBxdGUzV09MY0ciLCJzdWIiOjI5LCJwcnYiOiIyM2JkNWM4OTQ5ZjYwMGFkYjM5ZTcwMWM0MDA4NzJkYjdhNTk3NmY3In0.5BeiHo9qppwXB6Unp5OVFeta8AArCDp1goMzP_Gza5U")

            chain.proceed(builder.build())
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logsInterceptor)
            .addInterceptor(headerInterceptor)
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