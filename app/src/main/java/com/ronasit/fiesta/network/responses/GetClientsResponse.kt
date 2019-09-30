package com.ronasit.fiesta.network.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetClientsResponse {
    @SerializedName("current_page")
    @Expose
    var currentPage: Int = 1

    @SerializedName("data")
    @Expose
    var data: Array<ClientResponse> = emptyArray()

    @SerializedName("total")
    @Expose
    var total: Int = 0
}