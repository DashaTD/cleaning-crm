package com.ronasit.fiesta.ui.login.profile

import com.ronasit.fiesta.model.User
import com.ronasit.fiesta.network.requests.ProfileRequest
import com.ronasit.fiesta.service.db.UserService
import com.ronasit.fiesta.ui.base.BaseViewModel
import com.ronasit.fiesta.ui.login.LoginVM
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
        if (isInputValid()) {
            onValidInput()
        } else {
            onInvalidInput()
        }
    }

    private fun isInputValid(): Boolean {
        return profile.isValid()
    }

    private fun onValidInput() {
        loginVM.isProfileValid.value = true
        sendProfileRequest()
    }

    private fun onInvalidInput() {
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
            ).subscribeOn(Schedulers.newThread()).subscribe({
                if (it.code() == 401) {
                    onAuthorizationError()
                }
                if (it.code() == 200) {
                    loginVM.updateProfile(User.createUser(it.body()!!))
                    loginVM.moveToScheduleFragment()
                }
            }, {
                onProfileRequestError(it)
            })
        )
    }

    private fun onAuthorizationError() {
        // TODO:
    }

    private fun onProfileRequestError(throwable: Throwable) {
        //TODO: notify user of occurred error

    }

    fun onBackClick() {
        loginVM.backToSignInFragment()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}