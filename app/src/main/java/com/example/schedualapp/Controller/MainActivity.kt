package com.example.schedualapp.Controller

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ServiceCompat.stopForeground
import androidx.core.content.ContextCompat
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
    val adapter = GroupAdapter<ViewHolder>()
    lateinit var db: DataHelper
    lateinit var sizeOfDataBase: List<StatusDetails>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        db = DataHelper(this)
        sizeOfDataBase = db.allActivityList
        refreshList()

        callBtn.setOnClickListener { view ->
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }



        adapter.setOnItemClickListener { item, view ->
            Toast.makeText(this, "cliecked just", Toast.LENGTH_LONG).show()
        }



        Handler(Looper.getMainLooper()).post(object : Runnable {
            override fun run() {
                updateList()
                Handler().postDelayed(this, 500)
            }
        })

        startServices()
        checkContactPermission()
        checkLocationPermission()
        checkPhoneCallPermission()


//        sizeOfDataBase = db.allActivityList
        val layoutManager = LinearLayoutManager(this)
        statusList.layoutManager = layoutManager

    }


    private fun checkContactPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            GeneralMethods(this, contentResolver).requestContactPermission(this)
            return
        }
    }

    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            GeneralMethods(this, contentResolver).requestLocationPermission(this)
            return
        }

//        GeneralMethods(this,contentResolver).saveContact()

    }

    private fun checkPhoneCallPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            GeneralMethods(this, contentResolver).requestPhoneCallPermission(this)
            return
        }

    }


    fun startServices() {
        if (android.os.Build.VERSION.SDK_INT >= 26) {
            ContextCompat.startForegroundService(this, Intent(this, JobServices::class.java))
        } else {
            startService(Intent(this, JobServices::class.java))
        }
    }

    fun updateList() {
        if (sizeOfDataBase.size != db.allActivityList.size) {
            adapter.notifyDataSetChanged()
            refreshList()
        } else {
        }

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

    override fun onDestroy() {
        if (Build.VERSION.SDK_INT >= 26) {
            ContextCompat.startForegroundService(this, Intent(this, JobServices::class.java))
        } else {
            startService(Intent(this, JobServices::class.java))
        }
        super.onDestroy()
    }

}



