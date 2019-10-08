package com.ronasit.fiesta.ui.schedule.client

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import com.ronasit.fiesta.R
import com.ronasit.fiesta.databinding.FragmentClientBinding
import com.ronasit.fiesta.di.ViewModelInjectionField
import com.ronasit.fiesta.di.qualifiers.ViewModelInjection
import com.ronasit.fiesta.model.Client
import com.ronasit.fiesta.network.ClientModel
import com.ronasit.fiesta.ui.base.BaseFragment
import com.ronasit.fiesta.ui.schedule.clients.creation.AddClientDialogFragment
import javax.inject.Inject

const val PHONE_CALL_PERMISSION_REQUEST = 10

class ClientFragment(private val client: Client) : BaseFragment() {

    override fun layoutRes() = R.layout.fragment_client

    @Inject
    @ViewModelInjection
    lateinit var clientVM: ViewModelInjectionField<ClientVM>

    companion object {

        fun newInstance(client: Client): ClientFragment {
            return ClientFragment(client)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentClientBinding.inflate(inflater, container, false)

        binding.viewModel = clientVM.get()

        clientVM.get().client = client

        with(clientVM.get()) {
            onClose.observe(this@ClientFragment, Observer { activity!!.onBackPressed() })
            onEditClick.observe(this@ClientFragment, Observer {
                showEditClientDialogFragment(
                    ClientModel.create(client)
                )
            })
            onPhoneCallClick.observe(this@ClientFragment, Observer { onMakePhoneCallClick() })
            onEmailClick.observe(this@ClientFragment, Observer { launchSMSApp() })
        }

        return binding.root
    }

    private fun showEditClientDialogFragment(clientModel: ClientModel) {
        clientModel.isEditting = true
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
                clientVM.get().loadClient()
                fragmentManager!!.unregisterFragmentLifecycleCallbacks(this)
            }
        }, false)

    }

    private fun onMakePhoneCallClick() {
        if (ContextCompat.checkSelfPermission(activity!!, Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                activity!!,
                arrayOf(Manifest.permission.CALL_PHONE), PHONE_CALL_PERMISSION_REQUEST
            )

        } else {
            makePhoneCall()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            PHONE_CALL_PERMISSION_REQUEST -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    makePhoneCall()
                }
                return
            }
            else -> {
            }
        }
    }

    private fun makePhoneCall() {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:${client.phoneNumber}")
        startActivity(intent)
    }

    private fun launchSMSApp() {
        val sendIntent = Intent(Intent.ACTION_VIEW)
        sendIntent.data = Uri.parse("smsto:" + client.phoneNumber)
        startActivity(sendIntent)

    }
}