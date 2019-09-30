package com.ronasit.fiesta.service.db

import com.ronasit.fiesta.model.Client
import com.ronasit.fiesta.service.IService
import io.realm.Realm

interface IClientsService : IService {
    fun insertClient(client: Client): Client
    fun findAll(): List<Client>
    fun findById(id: String): Client?
    fun updateClient(client: Client)
    fun deleteClient(client: Client)
    fun deleteClient(id: String)
    fun deleteAll()
}

class ClientsService : IClientsService {

    private var realm: Realm = Realm.getDefaultInstance()

    override fun insertClient(client: Client): Client {
        realm.beginTransaction()
        val realmClient = realm.copyToRealm(client)
        realm.commitTransaction()
        return realmClient
    }

    override fun findAll(): List<Client> {
        return realm.where(Client::class.java).findAll()
    }

    override fun findById(id: String): Client? {
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

    override fun deleteClient(id: String) {
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