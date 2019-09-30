package com.ronasit.fiesta.ui.schedule.appointments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject
import com.ronasit.fiesta.R
import com.ronasit.fiesta.databinding.FragmentAppointmentsBinding
import com.ronasit.fiesta.di.qualifiers.ViewModelInjection
import com.ronasit.fiesta.di.ViewModelInjectionField
import com.ronasit.fiesta.ui.base.BaseFragment

class AppointmentsFragment : BaseFragment() {

    override fun layoutRes() = R.layout.fragment_appointments

    //private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    companion object {
        fun newInstance(): AppointmentsFragment {
            return AppointmentsFragment()
        }
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<AppointmentsVM>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAppointmentsBinding.inflate(inflater, container, false)
        binding.recyclerList.layoutManager = LinearLayoutManager(context)
        val items = listOf(
            AppointmentItem(
                "firstN",
                "Adress 1",
                "9 AM",
                true,
                "+78453211234",
                "123421",
                "cleaning",
                "$10",
                "10 hours"
            ),
            AppointmentItem(
                "SecondN",
                "Adress 17",
                "10 AM",
                false,
                "+78453211235",
                "123431",
                "washing",
                "$15",
                "9 hours"
            ),
            AppointmentItem(
                "ThirdN",
                "72 Adress",
                "11 AM",
                true,
                "+78453211237",
                "123451",
                "building",
                "$20",
                "7 hours"
            ),
            AppointmentItem(
                "FouthN",
                "Adress 21",
                "12 AM",
                false,
                "+78453211240",
                "123481",
                "cleaning",
                "$25",
                "4 hours"
            ),
            AppointmentItem(
                "FivesN",
                "99 Adress",
                "1 PM",
                true,
                "+78453211244",
                "123101",
                "washing",
                "$20",
                "5 hours"
            ),
            AppointmentItem(
                "SixsN",
                "Adress 34",
                "2 PM",
                false,
                "+78453299934",
                "123721",
                "building",
                "$15",
                "17 hours"
            )
        )
        viewManager = LinearLayoutManager(this.context)
        viewAdapter = AppointmentsAdapter(items)
        binding.recyclerList.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }


        val searchView: SearchView = binding.searchviewAppointments

        searchView.setOnClickListener {
            searchView.isIconified = false
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                //TODO: implement search
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                //TODO: implement
                return true
            }
        })

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun addClick(view: View) {

    }
}