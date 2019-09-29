package com.ronasit.fiesta.ui.schedule.appointments

data class AppointmentItem(
    val name:String,
    val address:String,
    val time:String,
    val isConfirmed: Boolean,
    val phoneNumber:String,
    val accessCode:String,
    val typeOfWOrk:String,
    val price:String,
    val workDuration:String)