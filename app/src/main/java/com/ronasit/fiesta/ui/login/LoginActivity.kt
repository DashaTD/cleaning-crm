package com.ronasit.fiesta.ui.login

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.ronasit.fiesta.R
import com.ronasit.fiesta.di.ViewModelInjectionField
import com.ronasit.fiesta.di.qualifiers.ViewModelInjection
import com.ronasit.fiesta.ui.base.BaseActivity
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.fragment_confirmation.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_sign_in.*
import javax.inject.Inject

class LoginActivity : BaseActivity(), HasSupportFragmentInjector {

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<LoginVM>

    override fun layoutRes() = R.layout.activity_main

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(viewModel.get()) {
            navigationController =
                Navigation.findNavController(this@LoginActivity, R.id.navigationFragment)
            subscribe()
        }
    }

    override fun onBackPressed() {
        with(viewModel.get()) {
            navigationController.currentDestination?.let {
                when (it.id) {
                    R.id.confirmationFragment -> navigationController.popBackStack()
                    R.id.profileFragment -> moveToSignInFragment()
                    else -> super.onBackPressed()
                }
            } ?: super.onBackPressed()
        }
    }

    // подписываемся на переменные для отображения сообщения об ошибках
    // переменные были вынесены в класс LoginVM. LoginVM сейчас является общей для фрагментов
    @RequiresApi(Build.VERSION_CODES.M)
    private fun subscribe() {
        with(viewModel.get()) {
            isPhoneValid.observe(this@LoginActivity, Observer {
                if (!it) {
                    error_phone_hint_text.visibility = View.VISIBLE
                    phone_field.setBackgroundResource(R.drawable.rounded_error_border_edittext)
                } else {
                    phone_field.setBackgroundResource(R.drawable.rounded_normal_border_edittext)
                    error_phone_hint_text.visibility = View.INVISIBLE
                }
            })

            isCodeValid.observe(this@LoginActivity, Observer {
                if (!it) {
                    error_code_hint_text.visibility = View.VISIBLE
                    code_edit.setBackgroundResource(R.drawable.rounded_error_border_edittext)
                } else {
                    error_code_hint_text.visibility = View.INVISIBLE
                    code_edit.setBackgroundResource(R.drawable.rounded_normal_border_edittext)

                }

            })

            isProfileValid.observe(this@LoginActivity, Observer {
                if (it) {
                    error_firstName_hint_text.visibility = View.INVISIBLE
                    first_name_text.setBackgroundResource(R.drawable.rounded_normal_border_edittext)
                } else {
                    error_firstName_hint_text.visibility = View.VISIBLE
                    first_name_text.setBackgroundResource(R.drawable.rounded_error_border_edittext)
                }
            })

        }

    }
}