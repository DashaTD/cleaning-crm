package com.ronasit.fiesta.network.requests

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ProfileRequest(
    @SerializedName("first_name")
    @Expose
    var firstName: String = "",

    @SerializedName("second_name")
    @Expose
    var secondName: String = "",

    @SerializedName("email")
    @Expose
    var email: String = ""
)