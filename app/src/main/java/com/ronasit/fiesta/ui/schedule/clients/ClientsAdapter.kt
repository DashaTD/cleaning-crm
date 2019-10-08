package com.ronasit.fiesta.ui.schedule.clients

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ronasit.fiesta.R
import com.ronasit.fiesta.model.Client

class ClientsAdapter(
    private var clients: List<Client>,
    private val clientClickListener: ClientClickListener
) :
    RecyclerView.Adapter<ClientsAdapter.ClientsViewHolder>() {

    interface ClientClickListener {
        fun onClientClick(client: Client)
        fun onEditClientClick(client: Client)
        fun onDeleteClientClick(client: Client)
    }

    inner class ClientsViewHolder(val clientView: View) : RecyclerView.ViewHolder(clientView) {

        constructor(clientView: View, clientClickListener: ClientClickListener) : this(clientView) {
            val clientLayout: View = clientView.findViewById(R.id.layout_client)
            clientLayout.setOnClickListener { clientClickListener.onClientClick(clients[adapterPosition]) }

            val editOption: View = clientView.findViewById(R.id.option_edit)
            editOption.setOnClickListener { clientClickListener.onEditClientClick(clients[adapterPosition]) }

            val deleteOption: View = clientView.findViewById(R.id.option_delete)
            deleteOption.setOnClickListener { clientClickListener.onDeleteClientClick(clients[adapterPosition]) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClientsViewHolder {
        val clientView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_client, parent, false) as View

        return ClientsViewHolder(clientView, clientClickListener)
    }

    override fun onBindViewHolder(holder: ClientsViewHolder, position: Int) {
        val clientView = holder.clientView
        val client = clients[position]

        val nameTextView = clientView.findViewById<TextView>(R.id.textview_name)
        val lastName = if (client.lastName == null) "" else client.lastName
        nameTextView.text = "${client.firstName} $lastName"

        val addressTextView = clientView.findViewById<TextView>(R.id.textview_address)
        addressTextView.text = client.address

        val phoneNumberTextView = clientView.findViewById<TextView>(R.id.textview_phone_number)
        phoneNumberTextView.text = client.phoneNumber
    }

    override fun getItemCount() = clients.size

    fun setClients(clients: List<Client>) {
        this.clients = clients
        notifyDataSetChanged()
    }
}