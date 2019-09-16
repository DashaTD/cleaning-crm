package com.ronasit.fiesta.model

import io.realm.RealmObject

open class Settings : RealmObject() {
    var isConfirmationRequested: Boolean = true
    var confirmationMessage: String? = null
}