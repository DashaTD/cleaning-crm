package com.ronasit.fiesta.ui.login.profile

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import java.util.regex.Pattern

class Profile : BaseObservable() {

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

    private val pattern: Pattern =
        Pattern.compile("^[a-zA-Z0-9.!#\$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*\$")

    private fun validateFirstName(): Boolean {
        return firstName.isEmpty().not()
    }

    private fun validateSecondName(): Boolean {
        return secondName.isEmpty().not()
    }

    private fun validateEmail(): Boolean {
        return pattern.matcher(email.toString()).matches()
    }

    fun isComplete(): Boolean {
        return validateFirstName()
    }
}