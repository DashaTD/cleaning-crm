package com.ronasit.fiesta.ui.login

import com.ronasit.fiesta.model.User
import com.ronasit.fiesta.service.db.UserService
import com.ronasit.fiesta.ui.base.BaseViewModel
import javax.inject.Inject

class LoginVM @Inject constructor() : BaseViewModel() {
    private val userService: UserService by lazy { UserService() }

    fun isProfileExist(): Boolean {
        return userService.isUserExist()
    }

    fun isProfileCompleted(): Boolean {
        val user = userService.findUser()
        return if (user != null) userService.isUserCompleted(user) else false
    }

    fun createProfile(phoneNumber: String) {
        val user = User()
        user.phoneNumber = phoneNumber
        userService.insertUser(user)
    }

    fun getProfilePhoneNumber() = userService.findUser()?.phoneNumber

    override fun onCleared() {
        super.onCleared()
        userService.close()
    }
}