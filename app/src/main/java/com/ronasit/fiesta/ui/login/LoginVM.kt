package com.ronasit.fiesta.ui.login

import com.ronasit.fiesta.ui.base.BaseViewModel
import javax.inject.Inject

class LoginVM @Inject constructor() : BaseViewModel() {

    fun isProfileCompleted(): Boolean {
        return false
    }
}