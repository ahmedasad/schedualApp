package com.example.schedualapp.Controller

import android.app.*
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.schedualapp.Adapter.ContactAdapter
import com.example.schedualapp.Utility.DataHelper
import com.example.schedualapp.Model.StatusDetails
import com.example.schedualapp.R
import com.example.schedualapp.Utility.AppData
import com.example.schedualapp.Utility.GeneralMethods
import com.example.schedualapp.Utility.SCHEDULE_NAME
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.shedule_call.*
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*

class CreateItemActivity : AppCompatActivity() {
    var time = ""
    var scheduleName = ""
    var date = ""
    lateinit var db: DataHelper
    lateinit var preference: AppData
    var count: Int = 0
    lateinit var contacts: StringBuilder
    val adapter = GroupAdapter<ViewHolder>()
    lateinit var client: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        val context = this
        super.onCreate(savedInstanceState)
        scheduleName = intent.getStringExtra(SCHEDULE_NAME)
        fillView(scheduleName)

        preference = AppData(this)
        count = preference.getCount()

        db = DataHelper(this)

        preSetDateAndTime(scheduleName)

        adapter.setOnItemClickListener { item, view ->
            val Item = item as ContactAdapter
            val cont = Item.contact
            findViewById<EditText>(R.id.txtToCall).setText(cont?.name + " " + cont?.num)
            recView.visibility = View.GONE
        }

        checkForPermissions()
        if (scheduleName == "call") {

            val contact:List<String> = GeneralMethods(this,contentResolver).loadContacts(contentResolver).split("\n\n")

            txtToCall.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    if (p0!!.length > 0) {
                        GeneralMethods(context,contentResolver).filter(p0.toString(), adapter,contact)

//                    txtToCall.text = contact
                    } else {
//                    txtToCall.hint = ""
                        adapter.clear()
                    }
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (p0!!.length > 0) recView.visibility = View.VISIBLE
                    else recView.visibility = View.GONE

                }
            })
        }
    }

    fun fillView(scheduleName: String) {
        when (scheduleName) {
            "travel" -> setContentView(R.layout.shedule_travel)
            "meeting" -> setContentView(R.layout.shedule_meeting)
            "call" -> {
                setContentView(R.layout.shedule_call)

                recView.adapter = adapter
                recView.layoutManager = LinearLayoutManager(this)
            }
            "task" -> setContentView(R.layout.shedule_task)
            "dinner" -> setContentView(R.layout.shedule_dinner)
            "extra" -> setContentView(R.layout.shedule_extra)
        }
    }

    private fun checkForPermissions(){
        client = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(this, "Need to grant permission!", Toast.LENGTH_LONG).show()
            GeneralMethods(this,contentResolver).requestLocationPermission(this)

            return
        }
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(this, "Need to grant permission!", Toast.LENGTH_LONG).show()
            GeneralMethods(this,contentResolver).requestContactPermission(this)

            return
        }

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

        getLocationAndSaveFirstCategoryData(time, date, from, to, extraNote)
    }

    fun btnMeetingSaveClicked(view: View) {
        val with = findViewById<TextView>(R.id.txtWithMeeting).text.toString()
        val place = findViewById<TextView>(R.id.txtInMeeting).text.toString()
        val time = findViewById<TextView>(R.id.txtTimeSetMeeting).text.toString()
        val date = findViewById<TextView>(R.id.txtDateSetMeeting).text.toString()
        getLocationAndSaveThirdCategoryData(time, date, with, place)

    }

    fun btnCallSaveClicked(view: View) {
        val to = findViewById<TextView>(R.id.txtToCall).text.toString()
        val time = findViewById<TextView>(R.id.txtTimeSetCall).text.toString()
        val date = findViewById<TextView>(R.id.txtDateSetCall).text.toString()
        getLocationAndSaveFourthCategoryData(time, date, to)
    }

    fun btnTaskSaveClicked(view: View) {
        val work = findViewById<TextView>(R.id.txtWorkTask).text.toString()
        val time = findViewById<TextView>(R.id.txtTimeSetTask).text.toString()
        val date = findViewById<TextView>(R.id.txtDateSetTask).text.toString()
        getLocationAndSaveSecondCategoryData(time, date, work)
    }

    fun btnDinnerSaveClicked(view: View) {
        val with = findViewById<TextView>(R.id.txtWithDinner).text.toString()
        val place = findViewById<TextView>(R.id.txtInDinner).text.toString()
        val time = findViewById<TextView>(R.id.txtTimeSetDinner).text.toString()
        val date = findViewById<TextView>(R.id.txtDateSetDinner).text.toString()
        getLocationAndSaveThirdCategoryData(time, date, with, place)
    }

    fun btnExtraSaveClicked(view: View) {
        val time = findViewById<TextView>(R.id.txtTimeSetExtra).text.toString()
        val date = findViewById<TextView>(R.id.txtDateSetExtra).text.toString()
        val work = findViewById<TextView>(R.id.txtWorkExtra).text.toString()
        getLocationAndSaveSecondCategoryData(time, date, work)
    }

    fun getLocationAndSaveFirstCategoryData(time: String, date: String, from: String, to: String, extraNote: String) {
        count++
        preference.setCount(count)


        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            checkForPermissions()

            return
        }


        println("client___ $client")
        client.lastLocation.addOnSuccessListener { p0 ->
            if (p0 != null) {
                val location = GeneralMethods(this,contentResolver).getLocationName(p0.longitude, p0.latitude)
                val statusDetail = StatusDetails(count, scheduleName, date, time, from, to, extraNote, location)
                db.addActivity(statusDetail)
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else {
                println("location___" + p0.toString())
            }
        }

        println("Count..... $")


    }

    fun getLocationAndSaveSecondCategoryData(time: String, date: String, work: String) {

        count++
        preference.setCount(count)


        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            checkForPermissions()

            return
        }

        println("client___ $client")
        client.lastLocation.addOnSuccessListener { p0 ->
            if (p0 != null) {
                val location = GeneralMethods(this,contentResolver).getLocationName(p0.longitude, p0.latitude)
                val statusDetail = StatusDetails(count, scheduleName, date, time, work, location)
                db.addActivity(statusDetail)
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else {
                println("location___" + p0.toString())
            }
        }
        println("Count..... $0")


    }

    fun getLocationAndSaveThirdCategoryData(time: String, date: String, with: String, place: String) {

        count++
        preference.setCount(count)



        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
           checkForPermissions()
            return
        }

        client.lastLocation.addOnSuccessListener { p0 ->
            if (p0 != null) {
                val location = GeneralMethods(this,contentResolver).getLocationName(p0.longitude, p0.latitude)
                val statusDetail = StatusDetails(count, scheduleName, date, time, with, place, location, "", "")
                db.addActivity(statusDetail)
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else {
                println("location___" + p0.toString())
            }
        }
        println("Count..... ")


    }

    fun getLocationAndSaveFourthCategoryData(time: String, date: String, contact: String) {

        count++
        preference.setCount(count)

        val cont = contact.split(" ")

        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED

        ) {
           checkForPermissions()
            return
        }

        client.lastLocation.addOnSuccessListener { p0 ->
            if (p0 != null) {
                val location = GeneralMethods(this,contentResolver).getLocationName(p0.longitude, p0.latitude)
                val statusDetail = StatusDetails(count, scheduleName, date, time, cont[0], location, cont[1])
                db.addActivity(statusDetail)
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else {
            }
        }

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

    fun setDateClicked(view: View) {
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
            time = String.format("%01d:%02d %s", h, m, region)
            if (h > 12 && region == "PM") time = String.format("%01d:%02d %s", h-12, m, region)
//            if(h<12 && region == "AM") time = String.format("%01d:%02d %s", h+12, m, region)

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


}
