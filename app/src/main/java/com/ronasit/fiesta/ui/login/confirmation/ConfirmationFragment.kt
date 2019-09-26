package com.ronasit.fiesta.ui.login.confirmation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ronasit.fiesta.R
import com.ronasit.fiesta.databinding.FragmentConfirmationBinding
import com.ronasit.fiesta.di.ViewModelInjectionField
import com.ronasit.fiesta.di.qualifiers.ViewModelInjection
import com.ronasit.fiesta.ui.base.BaseFragment
import com.ronasit.fiesta.ui.login.LoginVM
import kotlinx.android.synthetic.main.fragment_confirmation.*
import javax.inject.Inject

class ConfirmationFragment : BaseFragment() {

    override fun layoutRes() = R.layout.fragment_confirmation

    companion object {
        fun newInstance(): ConfirmationFragment {
            return ConfirmationFragment()
        }
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<ConfirmationVM>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentConfirmationBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel.get()
        binding.lifecycleOwner = this

        viewModel.get().showProgress.observe(this, Observer {
            if (it) showProgress() else hideProgress()
        })

        val loginVM = ViewModelProviders.of(activity!!)
            .get(LoginVM::class.java.simpleName, LoginVM::class.java)
        viewModel.get().loginVM = loginVM

        binding.codeEdit.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                //code_edit.setBackgroundResource(R.drawable.rounded_normal_border_edittext)
            } else {
                code_edit.setBackgroundResource(R.drawable.rounded_normal_unfocused_edittext)
            }
        }
        binding.codeEdit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                code_edit.setBackgroundResource(R.drawable.rounded_normal_border_edittext)
                error_code_hint_text.visibility = View.INVISIBLE
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })


        return binding.root
    }

}