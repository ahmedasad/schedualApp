package com.example.schedualapp.Controller

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
    lateinit var sizeOfDataBase: List<StatusDetails>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GeneralMethods(this).requestContactPermission(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) return

        db = DataHelper(this)
        adapter = GroupAdapter()
        refreshList()

        sizeOfDataBase = db.allActivityList
        val layoutManager = LinearLayoutManager(this)
        statusList.layoutManager = layoutManager


        startService(Intent(this, JobServices::class.java))
        callBtn.setOnClickListener { view ->

            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)

        }

        adapter.setOnItemClickListener { item, view ->
            Toast.makeText(this, "cliecked just", Toast.LENGTH_LONG).show()
        }
        GeneralMethods(this).requestLocationPermission(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) return

        GeneralMethods(this).requestLocationPermission(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        )   {
            Toast.makeText(this, "Need to grant permission!", Toast.LENGTH_LONG).show()
            return
        }


        Handler(Looper.getMainLooper()).post(object : Runnable {
            override fun run() {
                updateList()
                Handler().postDelayed(this, 500)
            }
        })


    }
    fun updateList() {
                if (sizeOfDataBase.size != db.allActivityList.size) refreshList()
                else println("done..........")

    }

//    fun btnClicked(view: View) {
//        db.deleteDB(this)
//    }

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



