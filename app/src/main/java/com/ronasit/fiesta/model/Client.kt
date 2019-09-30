package com.ronasit.fiesta.model

import com.ronasit.fiesta.network.responses.ClientResponse
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Client : RealmObject() {
    @PrimaryKey
    var id: String = UUID.randomUUID().toString()

    var phoneNumber: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var email: String? = null
    var zipCode: String? = null
    var address: String? = null
    var accessCode: String? = null
    var accessInformation: String? = null


    companion object {
        fun create(clientResponse: ClientResponse): Client {
            val client = Client()
            client.phoneNumber = clientResponse.mobilePhone
            client.firstName = clientResponse.firstName
            client.lastName = clientResponse.lastName
            client.email = clientResponse.email
            client.address = clientResponse.address
            client.zipCode = clientResponse.zipCode
            client.accessCode = clientResponse.accessCode
            client.accessInformation = clientResponse.accessInformation
            return client

        }
    }
}