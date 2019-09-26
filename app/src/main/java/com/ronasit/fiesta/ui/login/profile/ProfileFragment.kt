package com.ronasit.fiesta.ui.login.profile

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ronasit.fiesta.R
import com.ronasit.fiesta.databinding.FragmentProfileBinding
import com.ronasit.fiesta.di.ViewModelInjectionField
import com.ronasit.fiesta.di.qualifiers.ViewModelInjection
import com.ronasit.fiesta.ui.base.BaseFragment
import com.ronasit.fiesta.ui.login.LoginVM
import kotlinx.android.synthetic.main.fragment_profile.*
import javax.inject.Inject

class ProfileFragment : BaseFragment() {

    override fun layoutRes() = R.layout.fragment_profile

    companion object {
        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<ProfileVM>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel.get()
        binding.lifecycleOwner = this

        viewModel.get().showProgress.observe(this, Observer {
            if (it) showProgress() else hideProgress()
        })

        val loginVM = ViewModelProviders.of(activity!!)
            .get(LoginVM::class.java.simpleName, LoginVM::class.java)
        viewModel.get().loginVM = loginVM

        binding.firstNameText.setOnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                first_name_text.setBackgroundResource(R.drawable.rounded_normal_unfocused_edittext)
            } else {
                first_name_text.setBackgroundResource(R.drawable.rounded_normal_border_edittext)
            }
        }
        binding.secondNameText.setOnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                second_name_text.setBackgroundResource(R.drawable.rounded_normal_unfocused_edittext)
            } else {
                second_name_text.setBackgroundResource(R.drawable.rounded_normal_border_edittext)
            }
        }

        binding.emailEditText.setOnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                email_edit_text.setBackgroundResource(R.drawable.rounded_normal_unfocused_edittext)
            } else {
                email_edit_text.setBackgroundResource(R.drawable.rounded_normal_border_edittext)
            }
        }

        binding.firstNameText.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                first_name_text.setBackgroundResource(R.drawable.rounded_normal_border_edittext)
                error_firstName_hint_text.visibility = View.INVISIBLE

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })
        return binding.root
    }
}