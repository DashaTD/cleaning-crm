package com.ronasit.fiesta.ui.schedule.clients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ronasit.fiesta.R
import com.ronasit.fiesta.databinding.FragmentClientsBinding
import com.ronasit.fiesta.di.ViewModelInjectionField
import com.ronasit.fiesta.di.qualifiers.ViewModelInjection
import com.ronasit.fiesta.model.Client
import com.ronasit.fiesta.network.ClientModel
import com.ronasit.fiesta.ui.base.BaseFragment
import com.ronasit.fiesta.ui.schedule.ScheduleActivity
import com.ronasit.fiesta.ui.schedule.client.ClientFragment
import com.ronasit.fiesta.ui.schedule.clients.creation.AddClientDialogFragment
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

        viewAdapter = viewModel.get().clientsAdapter

        recyclerView = binding.clientsRecyclerView.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        with(viewModel.get()) {
            clients.observe(this@ClientsFragment,
                Observer { clients -> if (clients.isEmpty()) onNoClientsToDisplay() else onClientsToDisplay() })

            onClientClick.observe(
                this@ClientsFragment,
                Observer { client -> showClientFragment(client) })

            onCreateOrEditClick.observe(
                this@ClientsFragment,
                Observer { clientModel -> showAddClientDialogFragment(clientModel) })

            onDeleteClientFail.observe(
                this@ClientsFragment,
                Observer { showClientDeletionFailedToast() })

            onInternetConnectionError.observe(
                this@ClientsFragment,
                Observer { showInternetConnectionErrorToast() })

            showProgress.observe(
                this@ClientsFragment,
                Observer { if (it) showProgress() else hideProgress() })

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

        viewModel.get().loadCachedClients()

        viewModel.get().loadRemoteClients()

        return binding.root
    }

    private fun onNoClientsToDisplay() {
        searchView_clients.visibility = View.INVISIBLE
        textView_no_clients.visibility = View.VISIBLE
    }

    private fun onClientsToDisplay() {
        searchView_clients.visibility = View.VISIBLE
        textView_no_clients.visibility = View.GONE
    }

    private fun showClientFragment(client: Client) {
        (activity!! as ScheduleActivity).switchFragment(ClientFragment(client))
    }

    private fun showAddClientDialogFragment(clientModel: ClientModel) {
        val addClientDialogFragment = AddClientDialogFragment.newInstance(clientModel)
        addClientDialogFragment.show(
            activity!!.supportFragmentManager,
            "client_dialog_fragment"
        )
        createOnDialogDestroyedListener()

    }

    private fun createOnDialogDestroyedListener() {
        fragmentManager!!.registerFragmentLifecycleCallbacks(object :
            FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
                super.onFragmentViewDestroyed(fm, f)
                viewModel.get().loadCachedClients()
                fragmentManager!!.unregisterFragmentLifecycleCallbacks(this)
            }
        }, false)

    }

    private fun showInternetConnectionErrorToast() {
        showToast(resources.getText(R.string.toast_internet_connection_error))
    }

    private fun showClientDeletionFailedToast() {
        showToast(resources.getText(R.string.toast_client_deletion_failed))
    }

    private fun showToast(text: CharSequence) {
        val toast = Toast.makeText(
            activity,
            text,
            Toast.LENGTH_SHORT
        )
        toast.show()
    }
}