package com.ronasit.fiesta.model

import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey
import java.util.*

open class Appointment: RealmObject(){
    @PrimaryKey
    var id: String = UUID.randomUUID().toString()
    var clientId: String? = null
    @Ignore
    var client: Client? = null
    var time: Date? = null
    var durationMinutes: Int? = null
    var price: Float? = null
    var activity: String? = null
    var note: String? = null
    var isConfirmationRequired: Boolean? = null
    var isConfirmed: Boolean = false

    fun fetchClient(): Client?{
        return realm.where(Client::class.java).equalTo("id", clientId).findFirst()
    }
}