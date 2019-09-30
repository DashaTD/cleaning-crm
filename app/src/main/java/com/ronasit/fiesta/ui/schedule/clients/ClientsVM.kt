package com.ronasit.fiesta.ui.schedule.clients

import androidx.lifecycle.MutableLiveData
import com.ronasit.fiesta.model.Client
import com.ronasit.fiesta.network.responses.ClientResponse
import com.ronasit.fiesta.service.db.ClientsService
import com.ronasit.fiesta.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ClientsVM @Inject constructor() : BaseViewModel() {

    private val clientsService: ClientsService by lazy { ClientsService() }

    val clients = MutableLiveData<Array<DisplayedClient>>()

    private fun Client.createDisplayedClient(): DisplayedClient {
        val lastName = if (this.lastName == null) "" else this.lastName
        val name = "${this.firstName} $lastName"
        val address = "${this.address}, ${this.zipCode}"
        var phoneNumber = ""
        this.phoneNumber?.let {
            phoneNumber = it
        }

        return DisplayedClient(name, address, phoneNumber)
    }

    fun loadCachedClients() {
        clients.value = createDisplayedClients(clientsService.findAll().toTypedArray())
    }

    fun loadRemoteClients() {
        compositeDisposable.add(
            fiestaApi.getClients().subscribeOn(
                Schedulers.newThread()
            )
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    it.body()?.let { it ->
                        val respondedClients: Array<ClientResponse> = it.data

                        val clients = Array(respondedClients.size) {
                            Client.create(respondedClients[it])
                        }

                        updateCachedClients(clients)
                        this.clients.value = createDisplayedClients(clients)
                    }
                }, {

                })
        )
    }

    private fun updateCachedClients(clients: Array<Client>) {
        clientsService.deleteAll()
        clients.forEach { clientsService.insertClient(it) }
    }

    private fun createDisplayedClients(clients: Array<Client>): Array<DisplayedClient> {
        return Array(clients.size) {
            clients[it].createDisplayedClient()
        }
    }

    fun onAddClientButtonClick() {
        //TODO: move to client creation fragment
    }

}