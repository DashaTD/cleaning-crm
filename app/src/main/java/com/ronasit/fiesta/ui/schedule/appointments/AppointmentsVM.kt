package com.ronasit.fiesta.ui.schedule.appointments

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ronasit.fiesta.base.SingleLiveEvent
import com.ronasit.fiesta.model.Appointment
import com.ronasit.fiesta.network.requests.AddAppointmentRequest
import com.ronasit.fiesta.network.responses.AddAppointmentResponse
import com.ronasit.fiesta.service.db.AppointmentsService
import com.ronasit.fiesta.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class AppointmentsVM @Inject constructor() : BaseViewModel() {

    private val appointmentService: AppointmentsService by lazy { AppointmentsService() }
    //val binding = FragmentAppointmentsBinding()
    val newFr = MutableLiveData<Boolean>()
    val response = MutableLiveData<AddAppointmentResponse>()
    val onFinished = SingleLiveEvent<Boolean>()

    fun onAddClick() {
        //TODO start fragment "new appointment"
        newFr.value = true
    }

    fun onDeleteClick() {
        //TODO add fun delete
    }

    fun onEditClick() {
        //TODO add fun edit
    }

    fun addAppointment(appointment: AddAppointmentRequest){
        compositeDisposable.add(
            fiestaApi.createAppointment(appointment).subscribeOn(
                Schedulers.newThread()
            )
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    when (it.code()) {
                        200 -> {
                            it.body()?.let { it ->
                                Log.d("success", "valid response")
                                val respondedAppointment: AddAppointmentResponse = it
                                appointmentService.insertAppointment(
                                    fillAppointment(
                                        respondedAppointment
                                    )
                                )
                                Log.d(
                                    "in local db",
                                    appointmentService.findById(respondedAppointment.id.toString()).toString()
                                )
                                onFinished.postValue(true)
                            }
                        }

                        400 -> {
                            Log.d("error", "bad request")
                            onFinished.postValue(false)
                        }
                        401 -> {
                            Log.d("error", "Unauthorized")
                            onFinished.postValue(false)
                        }

                    }
                }, {
                    Log.d("error", "some error")
                    onFinished.postValue(false)
                })
        )
    }

    private fun validateAppointment(appointment: AddAppointmentRequest): Boolean {
        return !(appointment.duration == 0 && appointment.price.equals("") && appointment.startedAt.equals(
            ""
        ))
    }

    fun fillAppointment(appointment: AddAppointmentResponse): Appointment {
        val newAppointment = Appointment()
        newAppointment.activity = appointment.activity
        newAppointment.clientId = appointment.clientId
        newAppointment.durationMinutes = appointment.duration
        newAppointment.isConfirmed = appointment.isConfirmed == 1
        newAppointment.isConfirmationRequired = appointment.requestConfirmation == 1
        newAppointment.note = appointment.notes
        newAppointment.price = appointment.price.toFloat()
        newAppointment.id = appointment.id.toString()
        newAppointment.time =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).parse(appointment.startedAt)
        return newAppointment
    }
}