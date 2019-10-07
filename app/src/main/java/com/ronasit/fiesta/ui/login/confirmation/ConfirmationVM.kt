package com.ronasit.fiesta.ui.login.confirmation

import androidx.lifecycle.MutableLiveData
import com.ronasit.fiesta.model.User
import com.ronasit.fiesta.network.requests.AuthorizeRequest
import com.ronasit.fiesta.network.responses.AuthorizeResponse
import com.ronasit.fiesta.service.db.UserService
import com.ronasit.fiesta.ui.base.BaseViewModel
import com.ronasit.fiesta.ui.login.LoginVM
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject


class ConfirmationVM @Inject constructor() : BaseViewModel() {

    private val userService: UserService by lazy { UserService() }

    val phoneNumber: String? = userService.findUser()?.phoneNumber
    val confirmationCode = MutableLiveData<String>()

    private val codeRegex =
        "(^\\d{4}$)".toRegex()


    lateinit var loginVM: LoginVM

    fun onConfirmClick() {
        if (!validateCode()) {
            onInvalidInput()
        } else {
            showProgress.value = true
            sendAuthorizeRequest()
        }
    }

    private fun validateCode(): Boolean {
        confirmationCode.value?.let { code ->
            if (codeRegex.matches(code)) return true
        }
        return false
    }

    private fun onInvalidInput() {
        loginVM.isCodeValid.value = false
    }

    private fun sendAuthorizeRequest() {
        phoneNumber?.let {
            compositeDisposable.add(
                fiestaApi.authorize(AuthorizeRequest(it, confirmationCode.value!!)).subscribeOn(
                    Schedulers.newThread()
                )
                    .observeOn(AndroidSchedulers.mainThread()).subscribe({
                        onSucceededAuthorization(it)
                        showProgress.value = false
                    }, {
                        onAuthorizationError(it)
                        showProgress.value = false
                    })
            )
        }
    }

    private fun onSucceededAuthorization(authorizeResponse: Response<AuthorizeResponse>) {
        authorizeResponse.body()?.let { authResponse ->
            loginVM.isCodeValid.value = true
            updateProfile(User.createUser(authResponse))

            when (authorizeResponse.code()) {
                200 -> loginVM.moveToScheduleActivity()
                202 -> loginVM.moveToProfileFragment()
            }
        }
    }

    private fun updateProfile(user: User) {
        userService.updateUser(user)
        sharedPreferences.edit().putString("authToken", user.token).apply()
    }

    private fun onAuthorizationError(throwable: Throwable) {
        //TODO: notify user of occurred error
    }

    fun onBackCLick() {
        loginVM.navigationController.popBackStack()
    }

    override fun onCleared() {
        super.onCleared()
        userService.close()
        compositeDisposable.clear()
    }
}