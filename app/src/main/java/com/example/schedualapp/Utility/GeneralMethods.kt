package com.example.schedualapp.Utility

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.provider.ContactsContract
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity
import com.example.schedualapp.Adapter.ContactAdapter
import com.example.schedualapp.Controller.MainActivity
import com.example.schedualapp.Model.Contact
import com.example.schedualapp.Model.StatusDetails
import com.example.schedualapp.R
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
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
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(context,intent,null)
        }))
            .setNegativeButton("No", ({ _, _ ->
                false
            }))


            .show()

    }

    fun requestLocationPermission(context: Activity) {
        ActivityCompat.requestPermissions(context, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
    }

    fun requestContactPermission(context: Activity) {
        ActivityCompat.requestPermissions(context, arrayOf(android.Manifest.permission.READ_CONTACTS), 1)
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

    fun loadContacts(contentResolver:ContentResolver):java.lang.StringBuilder {
        val strBuilder = StringBuilder()
        val contResolver = contentResolver
        val cursor = contResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)
        if (cursor!!.count > 0) {
            while (cursor.moveToNext()) {
                val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val hasPhoneNumber =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)).toInt()
                if (hasPhoneNumber > 0) {
                    val cursor2 = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
                        arrayOf(id), null
                    )

                    while (cursor2!!.moveToNext()) {
                        val phoneNum =
                            cursor2!!.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        strBuilder.append("$name ").append(phoneNum)
                            .append("\n\n")



                    }
                    cursor2.close()
                }
            }
        }
        cursor.close()
//
//        textView.text = strBuilder
        return strBuilder
    }

    fun filter(text:String,adapter: GroupAdapter<ViewHolder>,contentResolver: ContentResolver){

        adapter.clear()
        val contactName = loadContacts(contentResolver).split("\n\n")
        for(item in contactName){
            if (item.toLowerCase().contains(text.toLowerCase())){
                val contactInfo = item.split(" ")
//                return contactInfo[0]+" "+contactInfo[1]
                adapter.add(ContactAdapter(Contact(contactInfo[0],contactInfo[1])))
                adapter.notifyDataSetChanged()
            }
        }
//        return "Contact not found!"
    }

}