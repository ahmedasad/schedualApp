package com.example.schedualapp.Adapter

import android.widget.TextView
import com.example.schedualapp.Model.Contact
import com.example.schedualapp.R
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder

class ContactAdapter(val data: Contact): Item<ViewHolder>() {
    var contact:Contact? = null
    override fun getLayout(): Int {
        return R.layout.list_model
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.findViewById<TextView>(R.id.name).text = data.name
        viewHolder.itemView.findViewById<TextView>(R.id.number).text = data.num
        contact = Contact(data.name,data.num)
    }
}