package com.ronasit.fiesta.ui.schedule.settings

import javax.inject.Inject
import com.ronasit.fiesta.R
import com.ronasit.fiesta.di.qualifiers.ViewModelInjection
import com.ronasit.fiesta.di.ViewModelInjectionField
import com.ronasit.fiesta.ui.base.BaseFragment

class SettingsFragment : BaseFragment() {

    override fun layoutRes() = R.layout.fragment_settings

    companion object {
        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<SettingsVM>

}