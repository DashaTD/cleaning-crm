package com.ronasit.fiesta.ui.login.profile

import javax.inject.Inject
import com.ronasit.fiesta.R
import com.ronasit.fiesta.di.qualifiers.ViewModelInjection
import com.ronasit.fiesta.di.ViewModelInjectionField
import com.ronasit.fiesta.ui.base.BaseFragment

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

}