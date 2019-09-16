package com.ronasit.fiesta.ui.login

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.ronasit.fiesta.R
import com.ronasit.fiesta.base.SingleLiveEvent
import com.ronasit.fiesta.model.User
import com.ronasit.fiesta.service.db.UserService
import com.ronasit.fiesta.ui.base.BaseViewModel
import com.ronasit.fiesta.ui.login.confirmation.ConfirmationFragmentDirections
import com.ronasit.fiesta.ui.login.profile.ProfileFragmentDirections
import com.ronasit.fiesta.ui.login.signin.SignInFragmentDirections
import javax.inject.Inject

class LoginVM @Inject constructor() : BaseViewModel() {
    private val userService: UserService by lazy { UserService() }

    // android компонент отвечающий за навигацию, добавили его в activity_main и получаем
    // при старте LoginActivity. Описание навигции между фрагментами выполнено в файле login_graph
    lateinit var navigationController: NavController

    val isPhoneValid: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val isCodeValid: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val isProfileValid: SingleLiveEvent<Boolean> = SingleLiveEvent()

    override fun onCleared() {
        super.onCleared()
        userService.close()
    }

    fun isProfileExist(): Boolean {
        return userService.isUserExist()
    }

    fun isProfileCompleted(): Boolean {
        val user = userService.findUser()
        return if (user != null) userService.isUserCompleted(user) else false
    }

    fun createProfile(phoneNumber: String) {
        val user = User()
        user.phoneNumber = phoneNumber
        userService.insertUser(user)
    }

    fun getProfilePhoneNumber() = userService.findUser()?.phoneNumber

    // метод отвечающий за переход к фрагменту ConfirmationFragment
    // для перехода на новый фрагмент мы создаем определенный action,
    // в данном случае SignInFragmentDirections.actionSignInFragmentToConfirmationFragment
    fun moveToConfirmationFragment() {
        navigationController.navigate(
            SignInFragmentDirections.actionSignInFragmentToConfirmationFragment()
        )
    }

    // метод отвечающий за переход к фрагменту ProfileFragment
    // для перехода на новый фрагмент мы создаем определенный action,
    // в данном случае ConfirmationFragmentDirections.actionConfirmationFragmentToProfileFragment
    fun moveToProfileFragment() {
        navigationController.navigate(
            ConfirmationFragmentDirections.actionConfirmationFragmentToProfileFragment()
        )
    }

    // метод отвечающий за переход к фрагменту SignInFragment из ProfileFragment
    // для перехода на новый фрагмент мы создаем определенный action,
    // в данном случае ProfileFragmentDirections.actionProfileFragmentToSignInFragment
    // NavOptions.Builder().setPopUpTo(R.id.signInFragment, true).build() - отвечает за то, чтобы
    // переход из профиля на signin не был добавлен в backstack. это обеспечивает выход из приложения
    // когда мы на экране signin
    fun backToSignInFragment() {
        navigationController.navigate(
            ProfileFragmentDirections.actionProfileFragmentToSignInFragment(),
            NavOptions.Builder().setPopUpTo(R.id.signInFragment, true).build()
        )
    }
}