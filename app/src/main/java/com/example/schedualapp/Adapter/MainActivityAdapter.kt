package com.example.schedualapp.Adapter

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.example.schedualapp.Controller.MainActivity
import com.example.schedualapp.Model.DataHelper
import com.example.schedualapp.R
import com.example.schedualapp.Model.StatusDetails
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder

class MainActivityAdapter(val context: Context, val status: StatusDetails) : Item<ViewHolder>() {

    override fun getLayout(): Int {
        return R.layout.item_theme
    }
    override fun bind(viewHolder: ViewHolder, position: Int) {


        val title = viewHolder.itemView?.findViewById<TextView>(R.id.statusTitle)
        val time = viewHolder.itemView?.findViewById<TextView>(R.id.statusTime)
        val date = viewHolder.itemView?.findViewById<TextView>(R.id.statusDate)
        val detail = viewHolder.itemView?.findViewById<TextView>(R.id.statusDetail)
        val loc = viewHolder.itemView?.findViewById<TextView>(R.id.statusLocation)
        val img = viewHolder.itemView?.findViewById<ImageView>(R.id.statusImage)
        val delBtn = viewHolder.itemView.findViewById<ImageButton>(R.id.statusDeleteBtn)
        val resId = context.resources.getIdentifier("${status.title}_status", "drawable", context.packageName)

        img.setImageResource(resId)
        title.text = status.title
        time.text = status.time
        date.text = status.date
        loc.text = status.location
        if (status.title == "travel") {
            detail.text = "${status.from} to ${status.to}"
        } else if (status.title == "task") {
            detail.text = "${status.to}"
        } else if (status.title == "call") {
            detail.text = "${status.to}"
        } else if (status.title == "meeting") {
            detail.text = "${status.with} in ${status.place}"
        } else if (status.title == "dinner") {
            detail.text = "${status.with} in ${status.place}"
        } else {
            detail.text = "${status.to}"
        }


        delBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                deleteRow()
                DataHelper(context).deleteActivity(status)
            }

        })


    }

    fun deleteRow() {
        notifyChanged()
    }
}