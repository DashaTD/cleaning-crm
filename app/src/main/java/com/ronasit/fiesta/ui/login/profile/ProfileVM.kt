package com.ronasit.fiesta.ui.login.profile

import com.ronasit.fiesta.network.requests.ProfileRequest
import com.ronasit.fiesta.service.db.UserService
import com.ronasit.fiesta.ui.base.BaseViewModel
import com.ronasit.fiesta.ui.login.LoginVM
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProfileVM @Inject constructor() : BaseViewModel() {

    lateinit var loginVM: LoginVM

    private val userService: UserService by lazy { UserService() }

    val profile: Profile by lazy {
        Profile().apply {
            fillInProfile(this)
        }
    }

    private fun fillInProfile(profile: Profile): Profile {
        val user = userService.findUser()
        if (!user?.lastName.isNullOrBlank()) {
            profile.secondName = user?.lastName.toString()
        }

        if (!user?.emailAddress.isNullOrEmpty()) {
            profile.email = user?.emailAddress.toString()
        }
        return profile
    }

    fun onConfirmClick() {
        if (profile.isComplete() && profile.isValid()) {
            onValidInput()
        } else {
            onInvalidInput()
        }
    }

    private fun onValidInput() {
        loginVM.isProfileValid.value = true
        showProgress.value = true
        sendProfileRequest()
    }

    private fun onInvalidInput() {
        loginVM.isProfileValid.value = false
        // TODO: notify user somehow
    }

    private fun sendProfileRequest() {
        compositeDisposable.add(
            fiestaApi.createProfile(
                ProfileRequest(
                    profile.firstName,
                    profile.secondName,
                    profile.email
                )
            ).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    when (it.code()) {
                        401 -> onAuthorizationError()
                        422 -> onInvalidInput()
                        204 -> onProfileRequestSuccess()
                    }
                    showProgress.value = false
                },
                    {
                        onProfileRequestError(it)
                        showProgress.value = false
                    })
        )
    }

    private fun onAuthorizationError() {
        userService.updateToken("")
    }

    private fun onProfileRequestSuccess() {
        userService.updateUser(profile)
        showProgress.value = false
        loginVM.moveToScheduleActivity()
    }

    private fun onProfileRequestError(throwable: Throwable) {
        //TODO: notify user of occurred error

    }

    fun onBackClick() {
        loginVM.moveToSignInFragment()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}