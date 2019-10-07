package com.ronasit.fiesta.network.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ClientResponse {

    @SerializedName("id")
    @Expose
    var id: Int = 0

    @SerializedName("mobile_phone")
    @Expose
    var mobilePhone: String = ""

    @SerializedName("first_name")
    @Expose
    var firstName: String = ""

    @SerializedName("last_name")
    @Expose
    var lastName: String = ""

    @SerializedName("email")
    @Expose
    var email: String = ""

    @SerializedName("address")
    @Expose
    var address: String = ""

    @SerializedName("zip_code")
    @Expose
    var zipCode: String = ""

    @SerializedName("access_code")
    @Expose
    var accessCode: String = ""

    @SerializedName("access_information")
    @Expose
    var accessInformation: String = ""

}