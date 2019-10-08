package com.ronasit.fiesta.ui.schedule.clients

import androidx.lifecycle.MutableLiveData
import com.ronasit.fiesta.base.SingleLiveEvent
import com.ronasit.fiesta.model.Client
import com.ronasit.fiesta.network.ClientModel
import com.ronasit.fiesta.network.responses.GetClientsResponse
import com.ronasit.fiesta.service.db.ClientsService
import com.ronasit.fiesta.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ClientsVM @Inject constructor() : BaseViewModel() {

    private val clientsService: ClientsService by lazy { ClientsService() }

    lateinit var clientsAdapter: ClientsAdapter

    val clients = MutableLiveData<List<Client>>()

    val onClientClick = SingleLiveEvent<Client>()

    val onCreateOrEditClick = SingleLiveEvent<ClientModel>()

    val onDeleteClientFail = SingleLiveEvent<Void>()

    val onInternetConnectionError = SingleLiveEvent<Void>()

    init {
        val clientClickListener: ClientsAdapter.ClientClickListener = createOnClientClickListener()
        clients.value?.let {
            clientsAdapter = ClientsAdapter(it, clientClickListener)
        } ?: run { clientsAdapter = ClientsAdapter(emptyList(), clientClickListener) }
    }

    fun loadCachedClients() {
        val clients = clientsService.findAll()
        this.clients.value = clients
        clientsAdapter.setClients(clients)
    }

    fun loadRemoteClients() {
        compositeDisposable.add(
            fiestaApi.getClients().subscribeOn(
                Schedulers.newThread()
            )
                .observeOn(AndroidSchedulers.mainThread()).subscribe({ response ->
                    if (response.code() == 200) {
                        response.body()?.let { onGetClientsRequestSuccess(it) }
                    }
                }, {
                    onInternetConnectionError.call()
                })
        )
    }

    private fun onGetClientsRequestSuccess(getClientsResponse: GetClientsResponse) {
        val respondedClients: Array<ClientModel> = getClientsResponse.data

        val clients = respondedClients.map {
            Client.create(it)
        }

        updateCachedClients(clients)
        this.clients.value = clients
        clientsAdapter.setClients(clients)
    }

    private fun updateCachedClients(clients: List<Client>) {
        clientsService.deleteAll()
        clientsService.insertClients(clients)
    }

    fun onAddButtonClick() {
        onCreateOrEditClick.value = ClientModel()
    }

    private fun createOnClientClickListener() = object : ClientsAdapter.ClientClickListener {
        override fun onClientClick(client: Client) {
            onClientClick.value = client
        }

        override fun onEditClientClick(client: Client) {
            val clientModel = ClientModel.create(client)
            clientModel.isEditting = true
            onCreateOrEditClick.value = clientModel
        }

        override fun onDeleteClientClick(client: Client) {
            showProgress.value = true
            sendDeleteClientRequest(client.id)
        }
    }

    private fun sendDeleteClientRequest(clientId: Int) {
        compositeDisposable.add(

            fiestaApi.deleteClient(clientId).subscribeOn(
                Schedulers.newThread()
            )
                .observeOn(AndroidSchedulers.mainThread()).subscribe({ response ->
                    when (response.code()) {
                        204 -> {
                            clientsService.deleteClient(clientId)
                            loadCachedClients()
                        }
                        403 -> onDeleteClientFail.call()
                    }
                    showProgress.value = false

                }, {
                    onInternetConnectionError.call()
                    showProgress.value = false
                })
        )
    }

}

