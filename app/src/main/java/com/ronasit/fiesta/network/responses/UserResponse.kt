package com.ronasit.fiesta.network.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserResponse {
    @SerializedName("first_name")
    @Expose
    var firstName: String = ""

    @SerializedName("email")
    @Expose
    var email: String = ""

    @SerializedName("zip_code")
    @Expose
    var zipCode: String = ""

    @SerializedName("mobile_phone")
    @Expose
    var mobilePhone: String = ""

    @SerializedName("last_name")
    @Expose
    var lastName: String = ""

    @SerializedName("access_code")
    @Expose
    var accessCode: String = ""

    @SerializedName("access_information")
    @Expose
    var accessInformation: String = ""
}