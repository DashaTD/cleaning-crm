package com.ronasit.fiesta.ui.schedule.appointments

import javax.inject.Inject
import com.ronasit.fiesta.R
import com.ronasit.fiesta.di.qualifiers.ViewModelInjection
import com.ronasit.fiesta.di.ViewModelInjectionField
import com.ronasit.fiesta.ui.base.BaseFragment

class AppointmentsFragment : BaseFragment() {

    override fun layoutRes() = R.layout.fragment_appointments

    companion object {
        fun newInstance(): AppointmentsFragment {
            return AppointmentsFragment()
        }
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<AppointmentsVM>

}