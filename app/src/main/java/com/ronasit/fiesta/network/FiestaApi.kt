package com.ronasit.fiesta.network

import com.ronasit.fiesta.network.requests.AuthorizeRequest
import com.ronasit.fiesta.network.requests.GetCodeRequest
import com.ronasit.fiesta.network.requests.ProfileRequest
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface FiestaApi {

    @POST("/api/get-code")
    fun getCode(@Body request: GetCodeRequest): Completable

    @POST("/api/authorize")
    fun authorize(@Body request: AuthorizeRequest): Single<Void>

    @PUT("/api/profile")
    fun createProfile(@Body request: ProfileRequest): Single<Void>
}