package com.ronasit.fiesta.ui.schedule.client

import com.ronasit.fiesta.base.SingleLiveEvent
import com.ronasit.fiesta.model.Client
import com.ronasit.fiesta.service.db.ClientsService
import com.ronasit.fiesta.ui.base.BaseViewModel
import javax.inject.Inject

class ClientVM @Inject constructor() : BaseViewModel() {

    private val clientsService: ClientsService by lazy { ClientsService() }

    lateinit var client: Client

    fun getClientName(): String = "${client.firstName} ${client.lastName ?: ""}"

    fun getPhoneNumber(): String = client.phoneNumber ?: ""

    fun getEmail(): String = client.email ?: ""

    fun getZipCode(): String = client.zipCode ?: ""

    fun getAddress(): String = client.address ?: ""

    fun getAccessCode(): String = client.accessCode ?: ""

    fun getAccessInformation(): String = client.accessInformation ?: ""

    val onClose = SingleLiveEvent<Void>()

    val onEditClick = SingleLiveEvent<Void>()

    val onPhoneCallClick = SingleLiveEvent<Void>()

    val onEmailClick = SingleLiveEvent<Void>()

    fun onBackButtonClick() {
        onClose.call()
    }

    fun onEditButtonClick() {
        onEditClick.call()
    }

    fun onPhoneCallButtonClick() {
        onPhoneCallClick.call()
    }

    fun onEmailButtonClick() {
        onEmailClick.call()
    }

    fun loadClient() {
        val updatedClient = clientsService.findById(client.id)
        if (updatedClient == null) onClose.call() else client = updatedClient
    }
}