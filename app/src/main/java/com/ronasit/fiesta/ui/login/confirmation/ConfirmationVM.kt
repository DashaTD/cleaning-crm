package com.ronasit.fiesta.ui.login.confirmation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ronasit.fiesta.base.SingleLiveEvent
import com.ronasit.fiesta.service.db.UserService
import com.ronasit.fiesta.ui.base.BaseViewModel
import javax.inject.Inject

class ConfirmationVM @Inject constructor() : BaseViewModel() {

    val confirmationCode = MutableLiveData<String>()
    private val isCodeValid: SingleLiveEvent<Boolean> = SingleLiveEvent()
    private val userService: UserService by lazy { UserService() }
    val phoneNumber: String? = userService.findUser()?.phoneNumber

    @Inject
    lateinit var context: Context

    fun isCodeValid(): LiveData<Boolean> = isCodeValid

    fun onConfirmClick() {
        isCodeValid.value = validateCode()
    }

    private fun validateCode(): Boolean {
        confirmationCode.value?.let { code ->
            if (!code.isEmpty()) return true
        }
        return false
    }

    override fun onCleared() {
        super.onCleared()
        userService.close()
    }
}