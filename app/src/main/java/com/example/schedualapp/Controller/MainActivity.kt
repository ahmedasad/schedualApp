package com.example.schedualapp.Controller

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.ContactsContract
import android.view.View
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.schedualapp.Adapter.MainActivityAdapter
import com.example.schedualapp.Adapter.MainAdapter
import com.example.schedualapp.Location
import com.example.schedualapp.Model.DataHelper
import com.example.schedualapp.Model.StatusDetails
import com.example.schedualapp.R
import com.example.schedualapp.Utility.DataService
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var adapter: GroupAdapter<ViewHolder>
    var db = DataHelper(this)
    lateinit var sizeOfDataBase:List<StatusDetails>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = DataHelper(this)
        adapter = GroupAdapter()
        refreshList()

        sizeOfDataBase = db.allActivityList
        val layoutManager = LinearLayoutManager(this)
        statusList.layoutManager = layoutManager

        fab.setOnClickListener { view ->
            val intent = Intent(this, MenuActivity::class.java)
            finish()
            startActivity(intent)
        }

        updateList()
    }

    fun updateList() {
        Handler(Looper.getMainLooper()).post(object : Runnable {
            override fun run() {
                if(sizeOfDataBase.size != db.allActivityList.size) refreshList()
                else println("done..........")
                Handler().postDelayed(this, 1000)

            }
        })
    }

    fun btnClicked(view: View) {
        db.deleteDB(this)
    }

    fun refreshList() {
        adapter.clear()
        var listactivity: List<StatusDetails>
        listactivity = db.allActivityList
        for (i in 0..listactivity.size - 1)
            adapter.add(MainActivityAdapter(this, listactivity[i]))
        statusList.adapter = adapter
        adapter.notifyDataSetChanged()

    }

}



