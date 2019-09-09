package com.ronasit.fiesta.ui.login.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ronasit.fiesta.base.SingleLiveEvent
import javax.inject.Inject
import com.ronasit.fiesta.ui.base.BaseViewModel

class SignInVM @Inject constructor() : BaseViewModel() {

    private val phoneNumber = MutableLiveData<String>()
    private val isPhoneValid: SingleLiveEvent<Boolean> = SingleLiveEvent()

    private val phoneRegex = "(([+][(]?[0-9]{1,3}[)]?)|([(]?[0-9]{4}[)]?))\\s*[)]?[-\\s\\.]?[(]?[0-9]{1,3}[)]?([-\\s\\.]?[0-9]{3})([-\\s\\.]?[0-9]{3,4})".toRegex()

    fun onContinueClick() {
        if (validatePhone()) {
            isPhoneValid.value = true
        } else {

        }
    }

    fun isPhoneValid(): LiveData<Boolean> = isPhoneValid

    fun phoneNumber(): MutableLiveData<String> = phoneNumber

    private fun validatePhone(): Boolean {
        phoneNumber.value?.let {
            return phoneRegex.matches(it)
        } ?: return false
    }
}