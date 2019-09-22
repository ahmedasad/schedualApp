package com.example.schedualapp.Controller

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.schedualapp.Model.StatusDetails
import com.example.schedualapp.R
import com.example.schedualapp.Utility.ACTIVITY_ID
import com.example.schedualapp.Utility.GeneralMethods


class ShowDataFromNotification : AppCompatActivity() {

val REQUEST_CALL = 1
    lateinit var item: StatusDetails
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data = intent.getStringExtra(ACTIVITY_ID)
        val id = data.toInt()
        item = GeneralMethods(this,contentResolver).getItem(id)
        fillView()

    setTitle("")
    }



    fun fillView() {

        when (item.title) {
            "travel" -> {
                setContentView(R.layout.from_notification_travel)
                fillTravelView(item)
            }
            "call" -> {
                setContentView(R.layout.from_notification_call)
                fillCallView(item)
            }
            "extra" -> {
                setContentView(R.layout.from_notification_extra_task)
                fillTaskExtraView(item)
            }
            "task" -> {
                setContentView(R.layout.from_notification_extra_task)
                fillTaskExtraView(item)
            }
            "meeting" -> {
                setContentView(R.layout.from_notification_meeting_dinner)
                fillMeetingDinnerView(item)
            }
            "dinner" -> {
                setContentView(R.layout.from_notification_meeting_dinner)
                fillMeetingDinnerView(item)
            }
        }
    }

    private fun fillTravelView(item: StatusDetails) {
        findViewById<TextView>(R.id.txtTitleTravelNotification).text = "Travel"
        findViewById<TextView>(R.id.txtDetailTravelNotification).text ="You scheduled your journey "
        findViewById<TextView>(R.id.txtFromTravelNotification).text ="${item.from.substring(0, 1) + item.from.substring(1)}"
        findViewById<TextView>(R.id.txtToTravelNotification).text ="${item.to.substring(0, 1) + item.to.substring(1)}"
        findViewById<TextView>(R.id.txtTimeAndDateTravelNotification).text = "${item.date} ${item.time}"
    }

    private fun fillCallView(item: StatusDetails) {
        findViewById<TextView>(R.id.txtTitleCallNotification).text = "Call"
        findViewById<TextView>(R.id.txtDetailCallNotification).text =
            "Call to ${item.to.substring(0, 1) + item.to.substring(1)} scheduled"
        findViewById<TextView>(R.id.txtTimeAndDateCallNotification).text = "${item.date} at ${item.time}"

    }

    private fun fillTaskExtraView(item: StatusDetails) {
        findViewById<TextView>(R.id.txtTitleExtraTaskNotification).text =
            item.title.substring(0, 1).toUpperCase() + item.title.substring(1)
        findViewById<TextView>(R.id.txtDetailExtraTaskNotification).text =
            "${item.to.substring(0, 1).toUpperCase() + item.to.substring(1)}"
        findViewById<TextView>(R.id.txtTimeAndDateExtraTaskNotification).text = "${item.date} at ${item.time}"
    }

    private fun fillMeetingDinnerView(item: StatusDetails) {
        findViewById<TextView>(R.id.txtTitleMeetingDinnerNotification).text =
            item.title.substring(0, 1).toUpperCase() + item.title.substring(1)
        findViewById<TextView>(R.id.txtDetailMeetingDinnerNotification).text =
            "You scheduled your ${item.title.substring(0, 1) + item.title.substring(1)}"
        findViewById<TextView>(R.id.txtWithMeetingDinnerNotification).text ="with ${item.with.substring(0, 1).toUpperCase() + item.with.substring(1)}"
            findViewById<TextView>(R.id.txtPlaceMeetingDinnerNotification).text ="${item.place.substring(0, 1).toUpperCase() + item.place.substring(1)}"
        findViewById<TextView>(R.id.txtTimeAndDateMeetingDinnerNotification).text = "${item.date} at ${item.time}"
    }


    fun btnCloseClicked(view: View) {
        finish()
    }

    fun btnCallClicked(view:View){
        makePhoneCall()
        finish()
    }

    private fun makePhoneCall(){
        val num = item.contNum
        if(num.trim().length>0){
            if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.CALL_PHONE),REQUEST_CALL)
            }
            else{
                val dial = "tel:$num"
                finish()
                startActivity(Intent(Intent.ACTION_CALL,Uri.parse(dial)))
            }
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == REQUEST_CALL){
            if(grantResults.size>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                makePhoneCall()
            }
            else{
                Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show()
            }
        }
    }
}
