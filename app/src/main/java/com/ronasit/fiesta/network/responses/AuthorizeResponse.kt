package com.ronasit.fiesta.network.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AuthorizeResponse {
    @SerializedName("access_token")
    @Expose
    var accessToken: String = ""

    @SerializedName("token_type")
    @Expose
    var tokenType: String = ""

    @SerializedName("expires_in")
    @Expose
    var expiresIn: Int = 0

    @SerializedName("user")
    @Expose
    var userResponse: UserResponse? = null
}