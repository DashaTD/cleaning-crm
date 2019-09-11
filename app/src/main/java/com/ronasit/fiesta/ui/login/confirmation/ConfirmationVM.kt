package com.ronasit.fiesta.ui.login.confirmation


import android.app.Application
import android.opengl.ETC1.isValid
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ronasit.fiesta.R
import com.ronasit.fiesta.base.SingleLiveEvent
import com.ronasit.fiesta.databinding.FragmentConfirmationBinding
import javax.inject.Inject
import com.ronasit.fiesta.ui.base.BaseViewModel

class ConfirmationVM @Inject constructor() : BaseViewModel() {

    val confirmationCode = MutableLiveData<String>()
    private val isCodeValid: SingleLiveEvent<Boolean> = SingleLiveEvent()

    @Inject
    lateinit var context: Context

    fun isCodeValid(): LiveData<Boolean> = isCodeValid

    fun onConfirmClick() {
        isCodeValid.value = validateCode()
    }

    private fun validateCode(): Boolean{
        confirmationCode.value?.let { code ->
            if(!code.isEmpty()) return true
        }
        return false
    }
    }