package com.ronasit.fiesta.ui.schedule.clients

import javax.inject.Inject
import com.ronasit.fiesta.R
import com.ronasit.fiesta.di.qualifiers.ViewModelInjection
import com.ronasit.fiesta.di.ViewModelInjectionField
import com.ronasit.fiesta.ui.base.BaseFragment

class ClientsFragment : BaseFragment() {

    override fun layoutRes() = R.layout.fragment_clients

    companion object {
        fun newInstance(): ClientsFragment {
            return ClientsFragment()
        }
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<ClientsVM>

}