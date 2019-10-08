package com.ronasit.fiesta.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ronasit.fiesta.model.Client

class ClientModel {

    @SerializedName("id")
    @Expose
    var id: Int = 0

    @SerializedName("mobile_phone")
    @Expose
    var mobilePhone: String = ""

    @SerializedName("first_name")
    @Expose
    var firstName: String = ""

    @SerializedName("last_name")
    @Expose
    var lastName: String = ""

    @SerializedName("email")
    @Expose
    var email: String = ""

    @SerializedName("address")
    @Expose
    var address: String = ""

    @SerializedName("zip_code")
    @Expose
    var zipCode: String = ""

    @SerializedName("access_code")
    @Expose
    var accessCode: String = ""

    @SerializedName("access_information")
    @Expose
    var accessInformation: String = ""

    var isEditting = false

    private val emailRegex: Regex =
        "^[a-zA-Z0-9.!#\$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*\$".toRegex()

    private val phoneRegex =
        "(([+][(]?[0-9]{1,3}[)]?)|([(]?[0-9]{4}[)]?))\\s*[)]?[-\\s\\.]?[(]?[0-9]{1,3}[)]?([-\\s\\.]?[0-9]{3})([-\\s\\.]?[0-9]{3,4})".toRegex()

    fun isValid(): Boolean {
        return if (areRequiredFieldsNotEmpty()) {
            validateEmail() && validateMobilePhone()

        } else false
    }

    private fun areRequiredFieldsNotEmpty(): Boolean {
        return firstName.isNotEmpty() && mobilePhone.isNotEmpty() &&
                zipCode.isNotEmpty() && address.isNotEmpty() &&
                accessCode.isNotEmpty() && accessInformation.isNotEmpty()
    }

    private fun validateEmail(): Boolean {
        return if (email.isNotBlank()) emailRegex.matches(email) else true
    }

    private fun validateMobilePhone(): Boolean {
        return phoneRegex.matches(mobilePhone)
    }

    companion object {
        fun create(client: Client): ClientModel {
            val clientModel = ClientModel()
            with(clientModel) {
                id = client.id
                mobilePhone = client.phoneNumber ?: ""
                firstName = client.firstName ?: ""
                lastName = client.lastName ?: ""
                email = client.email ?: ""
                address = client.address ?: ""
                zipCode = client.zipCode ?: ""
                accessCode = client.accessCode ?: ""
                accessInformation = client.accessInformation ?: ""
            }

            return clientModel
        }
    }

}