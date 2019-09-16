package com.ronasit.fiesta.service.db

import com.ronasit.fiesta.model.Settings
import com.ronasit.fiesta.service.IService
import io.realm.Realm

interface ISettingsService : IService {
    fun findSettings(): Settings?
    fun updateSettings(settings: Settings)

}

class SettingsService() : ISettingsService {

    private var realm: Realm = Realm.getDefaultInstance()

    init {
        if (!isSettingsExist()) {
            val settings = Settings()
            settings.isConfirmationRequested = true
            settings.confirmationMessage = "Hello, do you confirm the appointment?"
            insertSettings(Settings())
        }
    }

    private fun isSettingsExist(): Boolean {
        return findSettings() != null
    }

    private fun insertSettings(settings: Settings) {
        realm.beginTransaction()
        realm.copyToRealm(settings)
        realm.commitTransaction()
    }

    override fun findSettings(): Settings? {
        return realm.where(Settings::class.java).findFirst()
    }

    override fun updateSettings(settings: Settings) {
        realm.beginTransaction()
        realm.insertOrUpdate(settings)
        realm.commitTransaction()
    }

    override fun close() {
        realm.close()
    }

}