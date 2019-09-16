package com.ronasit.fiesta.ui.login.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ronasit.fiesta.R
import com.ronasit.fiesta.databinding.FragmentProfileBinding
import com.ronasit.fiesta.di.ViewModelInjectionField
import com.ronasit.fiesta.di.qualifiers.ViewModelInjection
import com.ronasit.fiesta.ui.base.BaseFragment
import com.ronasit.fiesta.ui.login.LoginVM
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
//        (activity as AppCompatActivity).supportActionBar!!.show()
        binding.viewModel = viewModel.get()
        binding.lifecycleOwner = this

        val loginVM = ViewModelProviders.of(activity!!).get(LoginVM::class.java.simpleName, LoginVM::class.java)
        viewModel.get().loginVM = loginVM
        return binding.root
    }
}