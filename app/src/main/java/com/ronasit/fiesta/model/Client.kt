package com.ronasit.fiesta.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Client : RealmObject() {
    @PrimaryKey
    var id: String = UUID.randomUUID().toString()
    var phoneNumber: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var emailAddress: String? = null
    var zipCode: String? = null
    var address: String? = null
    var accessCode: String? = null
    var accessInformation: String? = null
}