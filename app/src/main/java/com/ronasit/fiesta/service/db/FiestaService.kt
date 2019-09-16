package com.ronasit.fiesta.service.db

import com.ronasit.fiesta.service.IService
import io.realm.Realm

interface IFiestaService : IService {
    fun deleteAllData()
}

class FiestaService : IFiestaService {

    private var realm: Realm = Realm.getDefaultInstance()

    override fun deleteAllData() {
        realm.beginTransaction()
        realm.deleteAll()
        realm.commitTransaction()
    }

    override fun close() {
        realm.close()
    }

}