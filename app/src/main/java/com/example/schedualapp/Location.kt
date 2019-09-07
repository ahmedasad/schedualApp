package com.example.schedualapp

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener
import java.io.IOException

class Location {
//    fun getLocationCoordinates(context: Context){
//        requestPermission(context as Activity)
//        val client = LocationServices.getFusedLocationProviderClient(context)
//        if (ActivityCompat.checkSelfPermission(
//                context,
//                android.Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) return
//
//        println("client___ $client")
//        client.lastLocation.addOnSuccessListener() { p0 ->
//            if (p0 != null) {
//                getLocationName(p0.longitude,p0.latitude,context)
//            } else {
//                println("location___" + p0.toString())
//            }
//        }
//    }
//
////    fun getLongAndLat(lon: Double, lat: Double, context: Context) {
////        getLocationName(lon, lat, context)
////
////    }
//
//
//    fun getLocationName(lon: Double, lat: Double, context: Context): String {
//        try {
//            val geocoder = Geocoder(context)
//            var address: List<Address>
//            address = geocoder.getFromLocation(lat, lon, 1)
//
//            val country = address.get(0).countryName
//            val city = address.get(0).locality
//            println("Country... $country City $city")
//
//            println("address.. $address")
////            return city
//        } catch (e: IOException) {
//
//        }
//        return ""
//    }
//
//

}