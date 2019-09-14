package com.example.schedualapp.Controller

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.schedualapp.Adapter.MainActivityAdapter
import com.example.schedualapp.Utility.DataHelper
import com.example.schedualapp.Model.StatusDetails
import com.example.schedualapp.R
import com.example.schedualapp.Utility.GeneralMethods
import com.example.schedualapp.BackgroundServices.JobServices
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var adapter: GroupAdapter<ViewHolder>
    var db = DataHelper(this)
    lateinit var dateandTime: String
    lateinit var sizeOfDataBase: List<StatusDetails>

    companion object {
        var Count = 0
        fun CountSeconds() {
            Count++
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = DataHelper(this)
        adapter = GroupAdapter()
        refreshList()

//        if(intent.hasExtra(ACTIVITY_ID)){
//            if(intent.getStringExtra(ACTIVITY_ID)!="asd"){
//            var ActivityId:String = intent.getStringExtra(ACTIVITY_ID)
//            val id = ActivityId!!.toInt()-48}

//        }

        sizeOfDataBase = db.allActivityList
        val layoutManager = LinearLayoutManager(this)
        statusList.layoutManager = layoutManager


        startService(Intent(this, JobServices::class.java))
        fab.setOnClickListener { view ->

            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)

        }

        adapter.setOnItemClickListener { item, view ->
            Toast.makeText(this, "cliecked just", Toast.LENGTH_LONG).show()
        }
        GeneralMethods(this).requestPermission(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) return
//        InfoBuilder(sizeOfDataBase[sizeOfDataBase.size - 1].id)


        updateList()

    }
    fun updateList() {
        Handler(Looper.getMainLooper()).post(object : Runnable {
            override fun run() {
                if (sizeOfDataBase.size != db.allActivityList.size) refreshList()
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



