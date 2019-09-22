package com.example.schedualapp.Adapter

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.schedualapp.Controller.MainActivity
import com.example.schedualapp.Utility.DataHelper
import com.example.schedualapp.R
import com.example.schedualapp.Model.StatusDetails
import com.example.schedualapp.Utility.GeneralMethods
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import java.lang.Exception

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
        title.text = status.title.substring(0,1).toUpperCase() + status.title.substring(1)
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


        delBtn.setOnClickListener {
            DataHelper(context).deleteActivity(status)
            notifyChanged()

        }

        viewHolder.itemView.setOnLongClickListener {
            Toast.makeText(context, "Long Clicked from main", Toast.LENGTH_LONG).show()
            val item = DataHelper(context).allActivityList.indexOf(status)
            val id = status.id
            println("item  ..."+item)
//            val data = DataHelper(context).allActivityList[item]
//            createGroupOnClick(data)

            true


        }

//        viewHolder.itemView.setOnLongClickListener {
//            Toast.makeText(context,"Long Clicked", Toast.LENGTH_LONG).show()
//            true
//        }


    }




}


