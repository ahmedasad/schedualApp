package com.example.schedualapp.Utility

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity
import com.example.schedualapp.Controller.MainActivity
import com.example.schedualapp.Model.StatusDetails
import com.example.schedualapp.R
import java.io.IOException

class GeneralMethods (val context: Context){


    val db = DataHelper(context)

    fun getItem(id: Int): StatusDetails {
        val item = db.allActivityList[0]
        val listactivity = db.allActivityList
        for (i in 0 until listactivity.size) {
            if (listactivity[i].id == id) {
                println("returning item")
                return listactivity[i]
            }
        }
        return item
    }

    fun btnCloseClick(item:StatusDetails) {

        val builder = AlertDialog.Builder(context)
        val dialogView = LayoutInflater.from(context).inflate(R.layout.deletion_confirmation,null)
        builder.setView(dialogView)
        builder.setPositiveButton("Yes", ({ _, _ ->
            db.deleteActivity(item)
            val intent = Intent(context,MainActivity::class.java)
            startActivity(context,intent,null)
        }))
            .setNegativeButton("No", ({ _, _ ->
                false
            }))


            .show()

    }

    fun requestPermission(context: Activity) {
        ActivityCompat.requestPermissions(context, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
    }

    fun getLocationName(lon: Double, lat: Double): String {
        try {
            val geocoder = Geocoder(context)
            var address: List<Address>
            address = geocoder.getFromLocation(lat, lon, 1)

            val country = address.get(0).countryName
            return address.get(0).locality
        } catch (e: IOException) {

        }
        return ""
    }
}