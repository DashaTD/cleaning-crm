package com.ronasit.fiesta.ui.schedule

import javax.inject.Inject
import com.ronasit.fiesta.R
import com.ronasit.fiesta.di.qualifiers.ViewModelInjection
import com.ronasit.fiesta.di.ViewModelInjectionField
import com.ronasit.fiesta.ui.base.BaseFragment

class ScheduleFragment : BaseFragment() {

    override fun layoutRes() = R.layout.fragment_schedule

    companion object {
        fun newInstance(): ScheduleFragment {
            return ScheduleFragment()
        }
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<ScheduleVM>

}