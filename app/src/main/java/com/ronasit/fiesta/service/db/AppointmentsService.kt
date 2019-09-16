package com.ronasit.fiesta.service.db

import com.ronasit.fiesta.model.Appointment
import com.ronasit.fiesta.service.IService
import io.realm.Realm

interface IAppointmentsService : IService {
    fun insertAppointment(appointment: Appointment): Appointment
    fun findAll(): List<Appointment>
    fun findById(id: String): Appointment?
    fun updateAppointment(appointment: Appointment)
    fun deleteAppointment(appointment: Appointment)
    fun deleteAppointment(id: String)
    fun areAppointmentsExists(): Boolean

}

class AppointmentsService : IAppointmentsService {

    private var realm: Realm = Realm.getDefaultInstance()

    override fun insertAppointment(appointment: Appointment): Appointment {
        realm.beginTransaction()
        val realmAppointment = realm.copyToRealm(appointment)
        realm.commitTransaction()
        return realmAppointment
    }

    override fun findAll(): List<Appointment> {
        return realm.where(Appointment::class.java).findAll()
    }

    override fun findById(id: String): Appointment? {
        return realm.where(Appointment::class.java).equalTo("id", id).findFirst()
    }

    override fun updateAppointment(appointment: Appointment) {
        if (!isAppointmentExist(appointment)) return
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(appointment)
        realm.commitTransaction()
    }

    private fun isAppointmentExist(appointment: Appointment): Boolean {
        return findById(appointment.id) != null
    }

    override fun deleteAppointment(appointment: Appointment) {
        deleteAppointment(appointment.id)
    }

    override fun deleteAppointment(id: String) {
        val realmAppointment = findById(id)
        realm.beginTransaction()
        realmAppointment!!.deleteFromRealm()
        realm.commitTransaction()
    }

    override fun areAppointmentsExists(): Boolean {
        return findAll().isNotEmpty()
    }

    override fun close() {
        realm.close()
    }
}