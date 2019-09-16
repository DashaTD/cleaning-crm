package com.ronasit.fiesta.ui.login.profile

import android.widget.EditText
import com.ronasit.fiesta.base.SingleLiveEvent
import com.ronasit.fiesta.ui.base.BaseViewModel
import com.ronasit.fiesta.ui.login.LoginVM
import javax.inject.Inject

class ProfileVM @Inject constructor() : BaseViewModel() {

    lateinit var loginVM: LoginVM

    //    var profile = Profile()
    val profile: Profile by lazy {
        Profile().apply {
            firstName = "new"
            secondName = "User"
        }
    }

    fun onConfirmClick() {
        loginVM.isProfileValid.value = profile.isComplete()
    }

    fun onBackClick() {
        loginVM.backToSignInFragment()
    }
}