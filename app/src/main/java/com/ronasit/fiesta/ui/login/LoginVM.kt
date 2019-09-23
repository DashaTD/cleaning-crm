package com.ronasit.fiesta.ui.login

import androidx.navigation.NavController
import com.ronasit.fiesta.R
import com.ronasit.fiesta.base.SingleLiveEvent
import com.ronasit.fiesta.service.db.UserService
import com.ronasit.fiesta.ui.base.BaseViewModel
import com.ronasit.fiesta.ui.login.confirmation.ConfirmationFragmentDirections
import com.ronasit.fiesta.ui.login.profile.ProfileFragmentDirections
import com.ronasit.fiesta.ui.login.signin.SignInFragmentDirections
import com.ronasit.fiesta.ui.login.splash.SplashFragmentDirections
import javax.inject.Inject

class LoginVM @Inject constructor() : BaseViewModel() {
    private val userService: UserService by lazy { UserService() }

    // android компонент отвечающий за навигацию, добавили его в activity_main и получаем
    // при старте LoginActivity. Описание навигции между фрагментами выполнено в файле login_graph
    lateinit var navigationController: NavController

    val isPhoneValid: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val isCodeValid: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val isProfileValid: SingleLiveEvent<Boolean> = SingleLiveEvent()

    fun moveToSignInFragment() {
        navigationController.currentDestination?.let {
            when (it.id) {
                R.id.splashFragment ->
                    navigationController.navigate(
                        SplashFragmentDirections.actionSplashFragmentToSignInFragment()
                    )
                R.id.profileFragment -> navigationController.navigate(
                    ProfileFragmentDirections.actionProfileFragmentToSignInFragment()
                )
            }
        }
    }

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

    fun moveToScheduleFragment() {
        navigationController.currentDestination?.let {
            when (it.id) {
                R.id.splashFragment -> navigationController.navigate(
                    SplashFragmentDirections.actionSplashFragmentToScheduleFragment()
                )
                R.id.confirmationFragment -> navigationController.navigate(
                    ConfirmationFragmentDirections.actionConfirmationFragmentToScheduleFragment()
                )
                R.id.profileFragment -> navigationController.navigate(
                    ProfileFragmentDirections.actionProfileFragmentToScheduleFragment()
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        userService.close()
    }
}