package com.ronasit.fiesta.ui.login.signin

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ronasit.fiesta.R
import com.ronasit.fiesta.databinding.FragmentSignInBinding
import com.ronasit.fiesta.di.ViewModelInjectionField
import com.ronasit.fiesta.di.qualifiers.ViewModelInjection
import com.ronasit.fiesta.ui.base.BaseFragment
import com.ronasit.fiesta.ui.login.LoginVM
import kotlinx.android.synthetic.main.fragment_sign_in.*
import javax.inject.Inject

class SignInFragment : BaseFragment() {

    override fun layoutRes() = R.layout.fragment_sign_in

    companion object {
        fun newInstance(): SignInFragment {
            return SignInFragment()
        }
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<SignInVM>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSignInBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel.get()
        binding.lifecycleOwner = this

        val loginVM = ViewModelProviders.of(activity!!)
            .get(LoginVM::class.java.simpleName, LoginVM::class.java)
        viewModel.get().loginVM = loginVM

        viewModel.get().showProgress.observe(this, Observer {
            if (it) showProgress() else hideProgress()
        })

        if (loginVM.isProfileExist()) {
            viewModel.get().setPhoneNumber(loginVM.getProfilePhoneNumber()!!)
        }

        binding.phoneField.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                //phone_field.setBackgroundResource(R.drawable.rounded_normal_border_edittext)
            } else {
                phone_field.setBackgroundResource(R.drawable.rounded_normal_unfocused_eddittext)
            }
        }
        binding.phoneField.addTextChangedListener( object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                error_phone_hint_text.visibility = View.INVISIBLE
                phone_field.setBackgroundResource(R.drawable.rounded_normal_border_edittext)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

        })

        return binding.root
    }
}