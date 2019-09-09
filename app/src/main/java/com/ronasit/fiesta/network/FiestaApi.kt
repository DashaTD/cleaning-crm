package com.ronasit.fiesta.network

import retrofit2.http.POST

interface FiestaApi {

    @POST("")
    fun sendSms()

    @POST("")
    fun verifyCode()

    @POST("")
    fun createProfile()
}