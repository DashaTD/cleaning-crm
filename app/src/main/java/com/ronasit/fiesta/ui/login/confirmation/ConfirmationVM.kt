package com.ronasit.fiesta.ui.login.confirmation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ronasit.fiesta.base.SingleLiveEvent
import javax.inject.Inject
import com.ronasit.fiesta.ui.base.BaseViewModel

class ConfirmationVM @Inject constructor() : BaseViewModel() {

    val confirmationCode = MutableLiveData<String>()
    private val isCodeValid: SingleLiveEvent<Boolean> = SingleLiveEvent()

    @Inject
    lateinit var context: Context

    fun isCodeValid(): LiveData<Boolean> = isCodeValid

    fun onConfirmClick() {
        isCodeValid.value = true
    }

}