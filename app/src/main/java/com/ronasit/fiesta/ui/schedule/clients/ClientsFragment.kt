package com.ronasit.fiesta.ui.schedule.clients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ronasit.fiesta.R
import com.ronasit.fiesta.databinding.FragmentClientsBinding
import com.ronasit.fiesta.di.ViewModelInjectionField
import com.ronasit.fiesta.di.qualifiers.ViewModelInjection
import com.ronasit.fiesta.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_clients.*
import javax.inject.Inject


class ClientsFragment : BaseFragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: ClientsAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun layoutRes() = R.layout.fragment_clients

    companion object {
        fun newInstance(): ClientsFragment {
            return ClientsFragment()
        }
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<ClientsVM>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentClientsBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel.get()
        binding.lifecycleOwner = this

        viewManager = LinearLayoutManager(activity!!.applicationContext)

        viewModel.get().clients.value?.let {
            viewAdapter = ClientsAdapter(it)
        } ?: run { viewAdapter = ClientsAdapter(emptyArray()) }


        recyclerView = binding.clientsRecyclerView.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        val searchView: SearchView = binding.searchViewClients

        searchView.setOnClickListener {
            binding.searchViewClients.isIconified = false
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

        createClientsObserver()

        viewModel.get().loadCachedClients()
        viewModel.get().loadRemoteClients()

        return binding.root
    }

    private fun createClientsObserver() {
        viewModel.get().clients.observe(this, Observer {
            if (it.isEmpty()) {
                onNoClientsToDisplay()
            } else onClientsToDisplay()
            viewAdapter.setClients(it)
        })
    }

    private fun onNoClientsToDisplay() {
        searchView_clients.visibility = View.INVISIBLE
        textView_no_clients.visibility = View.VISIBLE
    }

    private fun onClientsToDisplay() {
        searchView_clients.visibility = View.VISIBLE
        textView_no_clients.visibility = View.GONE
    }
}