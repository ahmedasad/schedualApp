package com.example.schedualapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.schedualapp.Model.StatusDetails
import com.example.schedualapp.R

class MainAdapter(val context: Context, val status: List<StatusDetails>) : RecyclerView.Adapter<MainAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View
        view = LayoutInflater.from(context).inflate(R.layout.item_theme, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return status.count()
    }

    override fun onBindViewHolder(Holder: Holder, position: Int) {
        Holder.bindStatus(context, status[position])
    }


    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView?.findViewById<TextView>(R.id.statusTitle)
        val time = itemView?.findViewById<TextView>(R.id.statusTime)
        val date = itemView?.findViewById<TextView>(R.id.statusDate)
        val detail = itemView?.findViewById<TextView>(R.id.statusDetail)
        val loc= itemView?.findViewById<TextView>(R.id.statusLocation)
        val img = itemView?.findViewById<ImageView>(R.id.statusImage)

        fun bindStatus(context: Context, status: StatusDetails) {
            val resId = context.resources.getIdentifier("${status.title}_status", "drawable", context.packageName)

            img.setImageResource(resId)
            title.text = status.title
            time.text = status.time
            date.text = status.date
            loc.text = status.location
            if (status.title == "travel") {
                detail.text = "${status.from} to ${status.to}"
            } else if(status.title == "task"){
                detail.text = "${status.workTask}"
            }
            else if(status.title == "call"){
                detail.text = "${status.to}"
            }
            else if(status.title == "meeting"){
                detail.text = "${status.with} in ${status.place}"
            }
            else if(status.title == "dinner"){
                detail.text = "${status.with} in ${status.place}"
            }
            else{
                detail.text = "${status.workExtra}"
            }
        }
    }
}