package com.ronasit.fiesta.network

import com.ronasit.fiesta.network.requests.AuthorizeRequest
import com.ronasit.fiesta.network.requests.GetCodeRequest
import com.ronasit.fiesta.network.requests.ProfileRequest
import com.ronasit.fiesta.network.responses.AuthorizeResponse
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface FiestaApi {

    @POST("/api/send-code")
    fun sendCode(@Body request: GetCodeRequest): Completable

    @POST("/api/authorize")
    fun authorize(@Body request: AuthorizeRequest): Single<Response<AuthorizeResponse>>

    @PUT("/api/profile")
    fun createProfile(@Body request: ProfileRequest): Single<Response<Void>>

    @POST("/api/refresh")
    fun refreshToken(): Call<Void>
}