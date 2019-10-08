package com.ronasit.fiesta.network.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ronasit.fiesta.network.ClientModel

class GetClientsResponse {

    @SerializedName("data")
    @Expose
    var data: Array<ClientModel> = emptyArray()

    @SerializedName("total")
    @Expose
    var total: Int = 0
}