package com.ronasit.fiesta.network

import android.content.SharedPreferences
import android.util.Log
import com.ronasit.fiesta.di.modules.NetworkModule
import okhttp3.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(private val sharedPref: SharedPreferences) : Interceptor, Authenticator {

    var authToken: String? = null
        get() {
            if (field == null) {
                field = sharedPref.getString("authToken", null)
            }
            return field
        }
        set(value) {
            if (field != value) {
                field = value
                sharedPref.edit().putString("authToken", value).apply()
            }
        }

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain
            .request()
            .newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")

        authToken?.let {
            builder.addHeader("Authorization", it)
        } ?: Log.d("TOKEN", "EMPTY TOKEN")

        return chain.proceed(builder.build())
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code == 401) {
            val refreshTokenCall = NetworkModule.fiestaApi.refreshToken()
            val refreshTokenResponse = refreshTokenCall.execute()

            if (refreshTokenResponse.code() == 204) {
                refreshTokenResponse.headers().get("Authorization")?.let { authorizationToken ->

                    authToken = authorizationToken

                    Log.d("TOKEN", "repeat with new token $authorizationToken")

                    return response.request.newBuilder()
                        .removeHeader("Authorization")
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