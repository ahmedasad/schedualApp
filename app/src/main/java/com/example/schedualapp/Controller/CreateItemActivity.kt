package com.example.schedualapp.Controller

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.example.schedualapp.Model.DataHelper
import com.example.schedualapp.Model.StatusDetails
import com.example.schedualapp.R
import com.example.schedualapp.Utility.AppData
import com.example.schedualapp.Utility.SCHEDULE_NAME
import com.google.android.gms.location.LocationServices
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CreateItemActivity : AppCompatActivity() {
    var time = ""
    var scheduleName = ""
    var date = ""
    lateinit var db: DataHelper
    lateinit var preference: AppData
    var count: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scheduleName = intent.getStringExtra(SCHEDULE_NAME)
        when (scheduleName) {
            "travel" -> setContentView(R.layout.shedule_travel)
            "meeting" -> setContentView(R.layout.shedule_meeting)
            "call" -> setContentView(R.layout.shedule_call)
            "task" -> setContentView(R.layout.shedule_task)
            "dinner" -> setContentView(R.layout.shedule_dinner)
            "extra" -> setContentView(R.layout.shedule_extra)
        }

        preference = AppData(this)
        count = preference.getCount()

        db = DataHelper(this)

        preSetDateAndTime(scheduleName)
    }

    fun btnCloseClicked(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        finish()
        startActivity(intent)
    }

    fun btnTravelSaveClicked(view: View) {
        val from = findViewById<TextView>(R.id.txtFromTravel).text.toString()
        val to = findViewById<TextView>(R.id.txtToTravel).text.toString()
        val extraNote = findViewById<TextView>(R.id.txtExtraNoteTravel).text.toString()
        val time = findViewById<TextView>(R.id.txtTimeSetTravel).text.toString()
        val date = findViewById<TextView>(R.id.txtDateSetTravel).text.toString()
        val timeBefore = findViewById<TextView>(R.id.txtMinBeforeTravel).text.toString()
        getLocationAndSaveFirstCategoryData(time, date, from, to, extraNote, timeBefore)
    }

    fun btnMeetingSaveClicked(view: View) {
        val with = findViewById<TextView>(R.id.txtWithMeeting).text.toString()
        val place = findViewById<TextView>(R.id.txtInMeeting).text.toString()
        val time = findViewById<TextView>(R.id.txtTimeSetMeeting).text.toString()
        val date = findViewById<TextView>(R.id.txtDateSetMeeting).text.toString()
        val timeBefore = findViewById<TextView>(R.id.txtMinBeforeMeeting).text.toString()
        getLocationAndSaveThirdCategoryData(time, date, with, place, timeBefore)

    }

    fun btnCallSaveClicked(view: View) {
        val to = findViewById<TextView>(R.id.txtToCall).text.toString()
        val time = findViewById<TextView>(R.id.txtTimeSetCall).text.toString()
        val date = findViewById<TextView>(R.id.txtDateSetCall).text.toString()
        val timeBefore = findViewById<TextView>(R.id.txtMinBeforeCall).text.toString()
        getLocationAndSaveSecondCategoryData(time, date, to, timeBefore)
    }

    fun btnTaskSaveClicked(view: View) {
        val work = findViewById<TextView>(R.id.txtWorkTask).text.toString()
        val time = findViewById<TextView>(R.id.txtTimeSetTask).text.toString()
        val date = findViewById<TextView>(R.id.txtDateSetTask).text.toString()
        val timeBefore = findViewById<TextView>(R.id.txtMinBeforeTask).text.toString()
        getLocationAndSaveSecondCategoryData(time, date, work, timeBefore)
    }

    fun btnDinnerSaveClicked(view: View) {
        val with = findViewById<TextView>(R.id.txtWithDinner).text.toString()
        val place = findViewById<TextView>(R.id.txtInDinner).text.toString()
        val time = findViewById<TextView>(R.id.txtTimeSetDinner).text.toString()
        val date = findViewById<TextView>(R.id.txtDateSetDinner).text.toString()
        val timeBefore = findViewById<TextView>(R.id.txtMinBeforeDinner).text.toString()
        getLocationAndSaveThirdCategoryData(time, date, with, place, timeBefore)
    }

    fun btnExtraSaveClicked(view: View) {
        val time = findViewById<TextView>(R.id.txtTimeSetExtra).text.toString()
        val date = findViewById<TextView>(R.id.txtDateSetExtra).text.toString()
        val work = findViewById<TextView>(R.id.txtWorkExtra).text.toString()
        val timeBefore = findViewById<TextView>(R.id.txtMinBeforeExtra).text.toString()
        getLocationAndSaveSecondCategoryData(time, date, work, timeBefore)
    }

    fun getLocationAndSaveFirstCategoryData(time: String,date: String,from: String,to: String,extraNote: String,timeBefore: String) {
        count++
        preference.setCount(count)


        requestPermission(this)
        val client = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) return

        println("client___ $client")
        client.lastLocation.addOnSuccessListener { p0 ->
            if (p0 != null) {
                val location = getLocationName(p0.longitude, p0.latitude)
                val statusDetail = StatusDetails(count, scheduleName, date, time, from, to, extraNote, timeBefore, location)
                db.addActivity(statusDetail)
                val intent = Intent(this, MainActivity::class.java)
                finish()
                startActivity(intent)
            } else {
                println("location___" + p0.toString())
            }
        }

        println("Count..... $")

    }

    fun getLocationAndSaveSecondCategoryData(time: String, date: String, work: String, timeBefore: String) {

        count++
        preference.setCount(count)

        requestPermission(this)
        val client = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) return

        println("client___ $client")
        client.lastLocation.addOnSuccessListener { p0 ->
            if (p0 != null) {
                val location = getLocationName(p0.longitude, p0.latitude)
                val statusDetail = StatusDetails(count, scheduleName, date, time, work, timeBefore, location)
                db.addActivity(statusDetail)
                val intent = Intent(this, MainActivity::class.java)
                finish()
                startActivity(intent)
            } else {
                println("location___" + p0.toString())
            }
        }
        println("Count..... $0")


    }

    fun getLocationAndSaveThirdCategoryData(time: String,date: String,with: String,place: String,timeBefore: String) {

        count++
        preference.setCount(count)

        requestPermission(this)
        val client = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) return

        println("client___ $client")
        client.lastLocation.addOnSuccessListener { p0 ->
            if (p0 != null) {
                val location = getLocationName(p0.longitude, p0.latitude)
                val statusDetail = StatusDetails(count, scheduleName, date, time, with, place, timeBefore, location)
                db.addActivity(statusDetail)
                val intent = Intent(this, MainActivity::class.java)
                finish()
                startActivity(intent)
            } else {
                println("location___" + p0.toString())
            }
        }
        println("Count..... ")


    }


    private fun preSetDateAndTime(layoutName: String) {
        val sdfTime = SimpleDateFormat("h:mm a")
        val sdfDate = SimpleDateFormat("d MMM, yyyy")

        time = sdfTime.format(Calendar.getInstance().time)
        date = sdfDate.format(Calendar.getInstance().time)

        when (layoutName) {
            "travel" -> {
                findViewById<TextView>(R.id.txtTimeSetTravel).text = time
                findViewById<TextView>(R.id.txtDateSetTravel).text = date

            }
            "meeting" -> {
                findViewById<TextView>(R.id.txtTimeSetMeeting).text = time
                findViewById<TextView>(R.id.txtDateSetMeeting).text = date
            }
            "call" -> {
                findViewById<TextView>(R.id.txtTimeSetCall).text = time
                findViewById<TextView>(R.id.txtDateSetCall).text = date
            }
            "task" -> {
                findViewById<TextView>(R.id.txtTimeSetTask).text = time
                findViewById<TextView>(R.id.txtDateSetTask).text = date
            }
            "dinner" -> {
                findViewById<TextView>(R.id.txtTimeSetDinner).text = time
                findViewById<TextView>(R.id.txtDateSetDinner).text = date
            }
            "extra" -> {
                findViewById<TextView>(R.id.txtTimeSetExtra).text = time
                findViewById<TextView>(R.id.txtDateSetExtra).text = date
            }

        }

    }

    fun setDateClicke(view: View) {
        val monthArray = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        println("Month::" + calendar.get(month))
        date = ""
        val datePick = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { datePicker, y, m, d ->
            date = String.format("%02d %s, %04d", d, monthArray[m], y)
            val format: String? = scheduleName
            when (format) {
                "travel" -> {
                    findViewById<TextView>(R.id.txtDateSetTravel).text = date

                }
                "meeting" -> {
                    findViewById<TextView>(R.id.txtDateSetMeeting).text = date
                }
                "call" -> {
                    findViewById<TextView>(R.id.txtDateSetCall).text = date
                }
                "task" -> {
                    findViewById<TextView>(R.id.txtDateSetTask).text = date
                }
                "dinner" -> {
                    findViewById<TextView>(R.id.txtDateSetDinner).text = date
                }
                "extra" -> {
                    findViewById<TextView>(R.id.txtDateSetExtra).text = date
                }
            }

        }, year, month, day)
        datePick.show()

    }

    fun setTimeClicked(view: View) {
        var region = ""
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        val sdfDate = SimpleDateFormat("d MMM, yyyy")
        val date = sdfDate.format(Calendar.getInstance().time)
        val timePick = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { timePicker, h, m ->
            if (h >= 12) {
                region = "PM"
            } else {
                region = "AM"
            }
            time = String.format("%02d:%02d %s", h, m, region)

            val format: String? = scheduleName
            when (format) {
                "travel" -> {
                    findViewById<TextView>(R.id.txtTimeSetTravel).text = time

                }
                "meeting" -> {
                    findViewById<TextView>(R.id.txtTimeSetMeeting).text = time
                }
                "call" -> {
                    findViewById<TextView>(R.id.txtTimeSetCall).text = time
                }
                "task" -> {
                    findViewById<TextView>(R.id.txtTimeSetTask).text = time
                }
                "dinner" -> {
                    findViewById<TextView>(R.id.txtTimeSetDinner).text = time
                }
                "extra" -> {
                    findViewById<TextView>(R.id.txtTimeSetExtra).text = time
                }
            }

        }, currentHour, currentMinute, false)
        timePick.show()
    }

    private fun requestPermission(context: Activity) {
        ActivityCompat.requestPermissions(context, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
    }

    fun getLocationName(lon: Double, lat: Double): String {
        try {
            val geocoder = Geocoder(this)
            var address: List<Address>
            address = geocoder.getFromLocation(lat, lon, 1)

            val country = address.get(0).countryName
            return address.get(0).locality
        } catch (e: IOException) {

        }
        return ""
    }

}
