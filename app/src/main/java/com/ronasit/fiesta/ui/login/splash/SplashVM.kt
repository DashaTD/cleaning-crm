package com.ronasit.fiesta.ui.login.splash

import android.os.Handler
import com.ronasit.fiesta.di.modules.NetworkModule
import com.ronasit.fiesta.service.db.UserService
import com.ronasit.fiesta.ui.base.BaseViewModel
import com.ronasit.fiesta.ui.login.LoginVM
import javax.inject.Inject

class SplashVM @Inject constructor() : BaseViewModel() {

    lateinit var loginVM: LoginVM

    private val userService: UserService by lazy { UserService() }

    private val SPLASH_DURATION_MILLISEC = 2000L

    init {
        Handler().postDelayed({
            if (isProfileCompleted()) {

                loginVM.moveToScheduleActivity()
            } else {
                loginVM.moveToSignInFragment()
            }
        }, SPLASH_DURATION_MILLISEC)
    }

    private fun isProfileCompleted(): Boolean {
        val user = userService.findUser()
        return if (user != null) userService.isUserCompleted(user) else false
    }
}