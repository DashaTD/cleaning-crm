package com.ronasit.fiesta.ui.login.signin

import androidx.lifecycle.MutableLiveData
import com.ronasit.fiesta.model.User
import com.ronasit.fiesta.network.requests.GetCodeRequest
import com.ronasit.fiesta.service.db.UserService
import com.ronasit.fiesta.ui.base.BaseViewModel
import com.ronasit.fiesta.ui.login.LoginVM
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class SignInVM @Inject constructor() : BaseViewModel() {

    lateinit var loginVM: LoginVM

    private val userService: UserService by lazy { UserService() }

    private val phoneNumber = MutableLiveData<String>()

    private val phoneRegex =
        "(([+][(]?[0-9]{1,3}[)]?)|([(]?[0-9]{4}[)]?))\\s*[)]?[-\\s\\.]?[(]?[0-9]{1,3}[)]?([-\\s\\.]?[0-9]{3})([-\\s\\.]?[0-9]{3,4})".toRegex()

    init {
        if (isProfileExist()) {
            phoneNumber.value = getProfilePhoneNumber()
        }
    }

    fun phoneNumber(): MutableLiveData<String> = phoneNumber

    private fun isProfileExist(): Boolean {
        return userService.isUserExist()
    }

    private fun getProfilePhoneNumber() = userService.findUser()?.phoneNumber

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
                    createProfile()
                    showProgress.value = false
                    loginVM.moveToConfirmationFragment()
                },
                    {
                        onSendCodeRequestError(it)
                        showProgress.value = false
                    })
        )
    }

    private fun createProfile() {
        val user = User()
        user.phoneNumber = phoneNumber.value
        userService.insertUser(user)
    }

    private fun onSendCodeRequestError(throwable: Throwable) {
        //TODO: notify user of occurred error
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}