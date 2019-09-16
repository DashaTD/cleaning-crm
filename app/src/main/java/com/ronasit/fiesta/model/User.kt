package com.ronasit.fiesta.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class User : RealmObject() {
    @PrimaryKey
    var phoneNumber: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var emailAddress: String? = null
    var token: String? = null
}