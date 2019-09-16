package com.ronasit.fiesta.ui.login.confirmation

import androidx.lifecycle.MutableLiveData
import com.ronasit.fiesta.service.db.UserService
import com.ronasit.fiesta.ui.base.BaseViewModel
import com.ronasit.fiesta.ui.login.LoginVM
import javax.inject.Inject

class ConfirmationVM @Inject constructor() : BaseViewModel() {

    private val userService: UserService by lazy { UserService() }

    val phoneNumber: String? = userService.findUser()?.phoneNumber
    val confirmationCode = MutableLiveData<String>()

    lateinit var loginVM: LoginVM

    fun onConfirmClick() {
        with (loginVM) {
            val isValid = validateCode()

            if (isValid) {
                moveToProfileFragment()
            } else {
                isCodeValid.value = isValid
            }
        }
        loginVM.isCodeValid.value = validateCode()
    }

    private fun validateCode(): Boolean {
        confirmationCode.value?.let { code ->
            if (!code.isEmpty()) return true
        }
        return false
    }

    fun onBackCLick(){
        loginVM.navigationController.popBackStack()
    }

    override fun onCleared() {
        super.onCleared()
        userService.close()
    }
}