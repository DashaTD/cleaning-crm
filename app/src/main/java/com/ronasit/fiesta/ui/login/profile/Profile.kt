package com.ronasit.fiesta.ui.login.profile

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import java.util.regex.Pattern

class Profile : BaseObservable() {

    private val emailPattern: Pattern =
        Pattern.compile("^[a-zA-Z0-9.!#\$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*\$")

    @Bindable
    var firstName = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.firstName)
        }
        get() = field

    @Bindable
    var secondName = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.secondName)
        }
        get() = field

    @Bindable
    var email = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.email)
        }
        get() = field

    private fun validateFirstName(): Boolean {
        // TODO: validate somehow differently?
        return firstName.isNotEmpty()
    }

    private fun validateSecondName(): Boolean {
        // TODO: validate somehow differently?
        return secondName.isNotEmpty()
    }

    private fun validateEmail(): Boolean {
        return emailPattern.matcher(email).matches()
    }

    fun isComplete(): Boolean {
        return firstName.isNotBlank()
    }

    fun isValid(): Boolean {
        val isFirstNameValid = validateFirstName()
        val isSecondNameValid = if (secondName.isNotBlank()) validateSecondName() else true
        val isEmailValid = if (email.isNotBlank()) validateEmail() else true
        return isFirstNameValid && isSecondNameValid && isEmailValid
    }
}