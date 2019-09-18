package com.ronasit.fiesta.di.modules

import com.google.gson.GsonBuilder
import com.ronasit.fiesta.BuildConfig
import com.ronasit.fiesta.network.FiestaApi
import com.ronasit.fiesta.service.db.UserService
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
object NetworkModule {

    private lateinit var fiestaApi: FiestaApi

    private val userService: UserService by lazy { UserService() }
    private val authToken: String
        get() {
            return userService.findUser()?.let {
                it.token
            } ?: ""
        }

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
                .addHeader("Authorization", authToken)

            chain.proceed(builder.build())
        }

        val authenticator = object : Authenticator {
            override fun authenticate(route: Route?, response: Response): Request? {
                if (response.code == 401) {
                    val refreshTokenCall = fiestaApi.refreshToken()
                    val refreshTokenResponse = refreshTokenCall.execute()

                    if (refreshTokenResponse.code() == 204) {
                        refreshTokenResponse.headers().get("Authorization")?.let { authorizationToken ->
                            userService.updateToken(authorizationToken)

                            return response.request.newBuilder()
                                .addHeader("Authorization", authorizationToken)
                                .build()
                        } ?: return null
                    } else {
                        return null
                    }
                } else {
                    return null
                }
            }

        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logsInterceptor)
            .addInterceptor(headerInterceptor)
            .authenticator(authenticator)
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