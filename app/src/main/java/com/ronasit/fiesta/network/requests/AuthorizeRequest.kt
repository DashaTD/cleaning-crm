package com.ronasit.fiesta.network.requests

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AuthorizeRequest(
    @SerializedName("mobile_phone")
    @Expose
    var mobilePhone: String = "",

    @SerializedName("verified_code")
    @Expose
    var verifyCode: String = ""
)