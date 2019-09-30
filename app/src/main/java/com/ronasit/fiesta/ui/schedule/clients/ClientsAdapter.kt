package com.ronasit.fiesta.ui.schedule.clients

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ronasit.fiesta.R

class ClientsAdapter(private var arrayOfClients: Array<DisplayedClient>) :
    RecyclerView.Adapter<ClientsAdapter.ClientsViewHolder>() {

    class ClientsViewHolder(val clientView: View) : RecyclerView.ViewHolder(clientView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClientsViewHolder {
        val clientView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_client, parent, false) as View

        return ClientsViewHolder(clientView)
    }

    override fun onBindViewHolder(holder: ClientsViewHolder, position: Int) {
        val clientView = holder.clientView
        val nameTextView = clientView.findViewById<TextView>(R.id.textview_name)
        nameTextView.text = arrayOfClients[position].name
        val addressTextView = clientView.findViewById<TextView>(R.id.textview_address)
        addressTextView.text = arrayOfClients[position].address
        val phoneNumberTextView = clientView.findViewById<TextView>(R.id.textview_phone_number)
        phoneNumberTextView.text = arrayOfClients[position].phoneNumber
    }

    override fun getItemCount() = arrayOfClients.size

    fun setClients(arrayOfClients: Array<DisplayedClient>) {
        this.arrayOfClients = arrayOfClients
        notifyDataSetChanged()
    }
}