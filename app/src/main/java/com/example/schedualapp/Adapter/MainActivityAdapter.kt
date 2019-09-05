package com.example.schedualapp.Adapter

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.example.schedualapp.R
import com.example.schedualapp.Model.StatusDetails
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder

class MainActivityAdapter(val context: Context,val status: StatusDetails):Item<ViewHolder>() {

    override fun getLayout(): Int {
        return R.layout.item_theme
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val title= viewHolder.itemView?.findViewById<TextView>(R.id.statusTitle)
        val time= viewHolder.itemView?.findViewById<TextView>(R.id.statusTime)
        val date = viewHolder.itemView?.findViewById<TextView>(R.id.statusDate)
        val detail= viewHolder.itemView?.findViewById<TextView>(R.id.statusDetail)
        val img = viewHolder.itemView?.findViewById<ImageView>(R.id.statusImage)

//        val resId = context.resources.getIdentifier(status.img,"drawable",context.packageName)

//        img.setImageResource(resId)
        title.text = status.title
        time.text = status.time
        date.text = status.date
//        detail.text = status.detail

    }

}