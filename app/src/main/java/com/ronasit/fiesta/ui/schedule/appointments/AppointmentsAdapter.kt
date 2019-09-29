package com.ronasit.fiesta.ui.schedule.appointments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ronasit.fiesta.R

class AppointmentsAdapter(var items: List<AppointmentItem>) :
    RecyclerView.Adapter<AppointmentsAdapter.AppointmentsVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentsVH {
        if (viewType == 1) return AppointmentsVH(
            LayoutInflater.from(parent.context).inflate(
                R.layout.appointment_up_next, parent, false
            )
        )
        else return AppointmentsVH(
            LayoutInflater.from(parent.context).inflate(
                R.layout.appointment_item, parent, false
            )
        )
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) 1 else 0
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: AppointmentsVH, position: Int) {
        if (position == 0) holder.bindUpComing(items[position])
        else holder.bind(items[position])
    }


    inner class AppointmentsVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindUpComing(item: AppointmentItem) {
            bind(item)
            val phone = itemView.findViewById<TextView>(R.id.number_label)
            val accessCode = itemView.findViewById<TextView>(R.id.access_code_label)
            val typeOfWork = itemView.findViewById<TextView>(R.id.type_of_work_label)
            val price = itemView.findViewById<TextView>(R.id.price_label)
            val durationOfWOrk = itemView.findViewById<TextView>(R.id.duration_label)
            phone.text = item.phoneNumber
            accessCode.text = item.accessCode
            typeOfWork.text = item.typeOfWOrk
            price.text = item.price
            durationOfWOrk.text = item.workDuration

        }

        fun bind(item: AppointmentItem) {
            val clientName = itemView.findViewById<TextView>(R.id.name_label)
            val clientAdress = itemView.findViewById<TextView>(R.id.address_label)
            val confirmed = itemView.findViewById<TextView>(R.id.confirmed_label)
            val timeOfItem = itemView.findViewById<TextView>(R.id.time_label)
            clientName.text = item.name
            clientAdress.text = item.address
            if (item.isConfirmed) {
                confirmed.setText(R.string.confirmed_text)
            } else {
                confirmed.setText("Not \n Confirmed")
            }
            timeOfItem.text = item.time

        }

    }

    interface Callback {
        fun onItemClicked(item: AppointmentItem)
    }
}