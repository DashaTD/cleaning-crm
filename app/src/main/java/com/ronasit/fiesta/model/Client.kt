package com.ronasit.fiesta.model

import com.ronasit.fiesta.network.ClientModel
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class Client : RealmObject() {

    @PrimaryKey
    var id: Int = 0
    var phoneNumber: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var email: String? = null
    var zipCode: String? = null
    var address: String? = null
    var accessCode: String? = null
    var accessInformation: String? = null

    companion object {
        fun create(clientModel: ClientModel): Client {
            val client = Client()
            with(client) {
                id = clientModel.id
                phoneNumber = clientModel.mobilePhone
                firstName = clientModel.firstName
                lastName = clientModel.lastName
                email = clientModel.email
                address = clientModel.address
                zipCode = clientModel.zipCode
                accessCode = clientModel.accessCode
                accessInformation = clientModel.accessInformation
            }

            return client
        }
    }
}