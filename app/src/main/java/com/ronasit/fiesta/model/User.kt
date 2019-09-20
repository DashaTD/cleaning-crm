package com.ronasit.fiesta.model

import com.ronasit.fiesta.network.responses.AuthorizeResponse
import com.ronasit.fiesta.network.responses.UserResponse
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class User : RealmObject() {
    @PrimaryKey
    var phoneNumber: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var emailAddress: String? = null
    var token: String? = null

    companion object {

        fun createUser(authorizeResponse: AuthorizeResponse): User {
            val userResponse = authorizeResponse.userResponse
            var user = User()
            userResponse?.let {
                user = createUser(userResponse)
            }
            user.token = "${authorizeResponse.tokenType} ${authorizeResponse.accessToken}"

            return user
        }

        fun createUser(userResponse: UserResponse): User {
            val user = User()
            user.phoneNumber = userResponse.mobilePhone
            user.firstName = userResponse.firstName
            user.lastName = userResponse.lastName
            user.emailAddress = userResponse.email
            return user
        }
    }
}