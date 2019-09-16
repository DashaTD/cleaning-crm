package com.ronasit.fiesta.network.requests

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetCodeRequest(
    @SerializedName("mobile_phone")
    @Expose
    var mobilePhone: String = ""
)