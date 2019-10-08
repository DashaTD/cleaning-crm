package com.ronasit.fiesta.service.db

import com.ronasit.fiesta.model.Client
import com.ronasit.fiesta.service.IService
import io.realm.Realm

interface IClientsService : IService {
    fun addClient(client: Client)
    fun insertClients(clients: List<Client>): List<Client>
    fun findAll(): List<Client>
    fun findById(id: Int): Client?
    fun updateClient(client: Client)
    fun deleteClient(client: Client)
    fun deleteClient(id: Int)
    fun deleteAll()
}

class ClientsService : IClientsService {

    private var realm: Realm = Realm.getDefaultInstance()

    override fun addClient(client: Client) {
        realm.beginTransaction()
        realm.copyToRealm(client)
        realm.commitTransaction()
    }

    override fun insertClients(clients: List<Client>): List<Client> {
        realm.executeTransaction {
            for (i in 0 until clients.size) {
                it.copyToRealm(clients[i])
            }
        }
        return findAll()
    }

    override fun findAll(): List<Client> {
        return realm.where(Client::class.java).findAll()
    }

    override fun findById(id: Int): Client? {
        return realm.where(Client::class.java).equalTo("id", id).findFirst()
    }

    override fun updateClient(client: Client) {
        if (!isClientExist(client)) return
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(client)
        realm.commitTransaction()
    }

    private fun isClientExist(client: Client): Boolean {
        return findById(client.id) != null
    }

    override fun deleteClient(client: Client) {
        deleteClient(client.id)
    }

    override fun deleteClient(id: Int) {
        val realmClient = findById(id)
        realm.beginTransaction()
        realmClient!!.deleteFromRealm()
        realm.commitTransaction()
    }

    override fun deleteAll() {
        realm.executeTransaction {
            it.delete(Client::class.java)
        }
    }

    override fun close() {
        realm.close()
    }

}