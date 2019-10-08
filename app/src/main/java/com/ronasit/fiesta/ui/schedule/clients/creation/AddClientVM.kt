package com.ronasit.fiesta.ui.schedule.clients.creation

import com.ronasit.fiesta.base.SingleLiveEvent
import com.ronasit.fiesta.model.Client
import com.ronasit.fiesta.network.ClientModel
import com.ronasit.fiesta.service.db.ClientsService
import com.ronasit.fiesta.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AddClientVM @Inject constructor() : BaseViewModel() {

    private val clientsService: ClientsService by lazy { ClientsService() }

    lateinit var clientModel: ClientModel

    val onClose = SingleLiveEvent<Void>()

    val onInternetConnectionError = SingleLiveEvent<Void>()

    fun onSaveButtonClick() {
        if (clientModel.isValid()) {
            showProgress.value = true
            if (clientModel.isEditting) sendUpdateClientRequest() else sendCreateClientRequest()
        }
    }

    private fun sendCreateClientRequest() {
        compositeDisposable.add(
            fiestaApi.createClient(clientModel).subscribeOn(
                Schedulers.newThread()
            )
                .observeOn(AndroidSchedulers.mainThread()).subscribe({ response ->
                    if (response.code() == 200) {
                        response.body()?.let {
                            clientsService.addClient(Client.create(it))
                            onClose.call()
                        }
                    }
                    showProgress.value = false
                }, {
                    onInternetConnectionError.call()
                    showProgress.value = false
                })
        )
    }

    private fun sendUpdateClientRequest() {
        compositeDisposable.add(
            fiestaApi.updateClient(clientModel.id, clientModel).subscribeOn(
                Schedulers.newThread()
            )
                .observeOn(AndroidSchedulers.mainThread()).subscribe({ response ->
                    if (response.code() == 204) {
                        clientsService.updateClient(Client.create(clientModel))
                        onClose.call()
                    }
                    showProgress.value = false
                }, {
                    onInternetConnectionError.call()
                    showProgress.value = false
                })
        )
    }

    fun onCancelButtonClick() {
        onClose.call()
    }

    fun isNewClient(): Boolean {
        return !clientModel.isEditting
    }

    fun getClientName() = "${clientModel.firstName} ${clientModel.lastName}"

}