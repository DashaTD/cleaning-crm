package com.ronasit.fiesta.ui.login.signin

import androidx.lifecycle.MutableLiveData
import com.ronasit.fiesta.network.requests.GetCodeRequest
import com.ronasit.fiesta.ui.base.BaseViewModel
import com.ronasit.fiesta.ui.login.LoginVM
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SignInVM @Inject constructor() : BaseViewModel() {

    lateinit var loginVM: LoginVM

    private val phoneNumber = MutableLiveData<String>()

    private val phoneRegex =
        "(([+][(]?[0-9]{1,3}[)]?)|([(]?[0-9]{4}[)]?))\\s*[)]?[-\\s\\.]?[(]?[0-9]{1,3}[)]?([-\\s\\.]?[0-9]{3})([-\\s\\.]?[0-9]{3,4})".toRegex()

    fun phoneNumber(): MutableLiveData<String> = phoneNumber

    fun setPhoneNumber(phoneNumber: String) {
        this.phoneNumber.value = phoneNumber
    }

    fun onContinueClick() {
        if (validatePhone()) {
            showProgress.value = true
            sendCodeRequest()
        } else {
            loginVM.isPhoneValid.value = false
        }
    }

    private fun validatePhone(): Boolean {
        phoneNumber.value?.let {
            return phoneRegex.matches(it)
        } ?: return false
    }

    private fun sendCodeRequest() {
        val phone = phoneNumber.value!!
        compositeDisposable.add(
            fiestaApi.sendCode(GetCodeRequest(phone)).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    loginVM.createProfile(phone)
                    showProgress.value = false
                    loginVM.moveToConfirmationFragment()
                },
                    {
                        onSendCodeRequestError(it)
                        showProgress.value = false
                    })
        )
    }

    private fun onSendCodeRequestError(throwable: Throwable) {
        //TODO: notify user of occurred error
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}