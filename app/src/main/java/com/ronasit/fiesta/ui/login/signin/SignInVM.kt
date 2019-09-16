package com.ronasit.fiesta.ui.login.signin

import androidx.lifecycle.MutableLiveData
import com.ronasit.fiesta.network.requests.GetCodeRequest
import com.ronasit.fiesta.ui.base.BaseViewModel
import com.ronasit.fiesta.ui.login.LoginVM
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SignInVM @Inject constructor() : BaseViewModel() {

    private val phoneNumber = MutableLiveData<String>()

    lateinit var loginVM: LoginVM

    private val phoneRegex =
        "(([+][(]?[0-9]{1,3}[)]?)|([(]?[0-9]{4}[)]?))\\s*[)]?[-\\s\\.]?[(]?[0-9]{1,3}[)]?([-\\s\\.]?[0-9]{3})([-\\s\\.]?[0-9]{3,4})".toRegex()

    fun onContinueClick() {
        with(loginVM) {
            if (validatePhone()) {
                val phone = phoneNumber.value!!

                compositeDisposable.add(
                    fiestaApi.getCode(GetCodeRequest(phone)).subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            loginVM.createProfile(phone)

                            moveToConfirmationFragment()
                        },
                            {
                                isPhoneValid.value = false
                            })
                )
            } else {
                isPhoneValid.value = false
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun phoneNumber(): MutableLiveData<String> = phoneNumber

    fun setPhoneNumber(phoneNumber: String) {
        this.phoneNumber.value = phoneNumber
    }

    private fun validatePhone(): Boolean {
        phoneNumber.value?.let {
            return phoneRegex.matches(it)
        } ?: return false
    }
}