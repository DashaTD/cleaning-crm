package com.ronasit.fiesta.network.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AddAppointmentResponse {
    @SerializedName("id")
    @Expose
    var id : Int = 0

    @SerializedName("client_id")
    @Expose
    var clientId: String = ""
    @SerializedName("user_id")
    @Expose
    var userId: String = ""

    @SerializedName("started_at")
    @Expose
    var startedAt: String = ""

    @SerializedName("duration")
    @Expose
    var duration: Int = 0

    @SerializedName("price")
    @Expose
    var price: Int = 0

    @SerializedName("activity")
    @Expose
    var activity: String = ""

    @SerializedName("notes")
    @Expose
    var notes: String = ""

    @SerializedName("request_confirmation")
    @Expose
    var requestConfirmation: Int = 0

    @SerializedName("is_confirmed")
    @Expose
    var isConfirmed: Int = 0

    @SerializedName("created_at")
    @Expose
    var createdAt: String = ""

    @SerializedName("updated_at")
    @Expose
    var updatedAt: String = ""
}