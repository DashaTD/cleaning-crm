package com.ronasit.fiesta.network.requests

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

class AddAppointmentRequest (

    @SerializedName("client_id")
    @Expose
    var client:Int = 8,

    @SerializedName("started_at")
    @Expose
    var startedAt:String = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(Date().time),

    @SerializedName("duration")
    @Expose
    var duration: Int = 0,

    @SerializedName("price")
    @Expose
    var price : String = "0",

    @SerializedName("activity")
    @Expose
    var activity:String = "",

    @SerializedName("notes")
    @Expose
    var notes:String = ""
) {
    fun fill() {

    }
}