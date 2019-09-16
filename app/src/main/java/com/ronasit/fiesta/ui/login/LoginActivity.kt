package com.ronasit.fiesta.ui.login

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.ronasit.fiesta.R
import com.ronasit.fiesta.di.ViewModelInjectionField
import com.ronasit.fiesta.di.qualifiers.ViewModelInjection
import com.ronasit.fiesta.ui.base.BaseActivity
import com.ronasit.fiesta.ui.login.confirmation.ConfirmationFragment
import com.ronasit.fiesta.ui.login.signin.SignInFragment
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class LoginActivity : BaseActivity(), HasSupportFragmentInjector {

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<LoginVM>

    override fun layoutRes() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(viewModel.get()) {
            if (isProfileExist() && isProfileCompleted()) {
                startAppointmentsActivity()
            }
            onMoveToSignInFragment()
        }
    }

    private fun onMoveToSignInFragment() {
        val signInFragment = SignInFragment.newInstance()
        supportFragmentManager.beginTransaction().replace(R.id.mainContainer, signInFragment)
            .commitNow()
        with(viewModel.get()) {
            if (isProfileExist()) {
                signInFragment.viewModel.get().setPhoneNumber(getProfilePhoneNumber()!!)
            }
        }
        signInFragment.viewModel.get().isPhoneValid().observe(this@LoginActivity, Observer {
            if (it) {
                val phoneNumber = signInFragment.viewModel.get().phoneNumber().value!!
                viewModel.get().createProfile(phoneNumber)
                onMoveToConfirmationFragment()
            } else {
                Toast.makeText(this, R.string.incorrect_phone_number_text, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun startAppointmentsActivity() {
        // TODO: open new activity
    }

    private fun onMoveToConfirmationFragment() {
        val confirmationFragment = ConfirmationFragment.newInstance()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.mainContainer, confirmationFragment)
        transaction.runOnCommit {
            confirmationFragment.viewModel.get().isCodeValid()
                .observe(this@LoginActivity, Observer {
                    if (it) {
                        Toast.makeText(this, "Valid code", Toast.LENGTH_SHORT).show()
                        onMoveToProfileFragment()
                    } else {
                        Toast.makeText(this, "Try again", Toast.LENGTH_SHORT).show()
                    }
                })
        }
        transaction.commit()

    }

    private fun onMoveToProfileFragment() {

    }

    override fun onBackPressed() {
        onMoveToSignInFragment()
    }

}