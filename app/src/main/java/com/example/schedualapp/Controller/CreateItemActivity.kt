package com.example.schedualapp.Controller

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.schedualapp.Model.DataHelper
import com.example.schedualapp.Model.StatusDetails
import com.example.schedualapp.R
import com.example.schedualapp.Utility.SCHEDULE_NAME
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CreateItemActivity : AppCompatActivity() {
    var time = ""
    var scheduleName = ""
    var date = ""
    lateinit var db: DataHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = DataHelper(this)
        scheduleName = intent.getStringExtra(SCHEDULE_NAME)

        when (scheduleName) {
            "travel" -> setContentView(R.layout.shedule_travel)
            "meeting" -> setContentView(R.layout.shedule_meeting)
            "call" -> setContentView(R.layout.shedule_call)
            "task" -> setContentView(R.layout.shedule_task)
            "dinner" -> setContentView(R.layout.shedule_dinner)
            "extra" -> setContentView(R.layout.shedule_extra)
        }

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
        val loc = "Pakistan"
        val statusDetail = StatusDetails(1, scheduleName, date, time, from, to, extraNote, timeBefore, loc)
        db.addActivity(statusDetail)
        Toast.makeText(this, "created field successfully", Toast.LENGTH_LONG).show()
        val intent = Intent(this, MainActivity::class.java)
        finish()
        startActivity(intent)
    }

    fun btnMeetingSaveClicked(view: View) {
        val with = findViewById<TextView>(R.id.txtWithMeeting).text.toString()
        val Place = findViewById<TextView>(R.id.txtInMeeting).text.toString()
        val time = findViewById<TextView>(R.id.txtTimeSetMeeting).text.toString()
        val date = findViewById<TextView>(R.id.txtDateSetMeeting).text.toString()
        val timeBefore = findViewById<TextView>(R.id.txtMinBeforeMeeting).text.toString()
        val loc = "Pakistan"
        val statusDetail = StatusDetails(2, scheduleName, date, time, with, Place, timeBefore, loc)
        db.addActivity(statusDetail)
        val intent = Intent(this, MainActivity::class.java)
        finish()
        startActivity(intent)

    }

    fun btnCallSaveClicked(view: View) {
        val to = findViewById<TextView>(R.id.txtToCall).text.toString()
        val time = findViewById<TextView>(R.id.txtTimeSetCall).text.toString()
        val date = findViewById<TextView>(R.id.txtDateSetCall).text.toString()
        val timeBefore = findViewById<TextView>(R.id.txtMinBeforeCall).text.toString()
        val loc = "Pakistan"
        val statusDetail = StatusDetails(3, scheduleName, date, time, to, timeBefore, loc)
        db.addActivity(statusDetail)
        val intent = Intent(this, MainActivity::class.java)
        finish()
        startActivity(intent)
    }

    fun btnTaskSaveClicked(view: View) {
        val work = findViewById<TextView>(R.id.txtWorkTask).text.toString()
        val time = findViewById<TextView>(R.id.txtTimeSetTask).text.toString()
        val date = findViewById<TextView>(R.id.txtDateSetTask).text.toString()
        val timeBefore = findViewById<TextView>(R.id.txtMinBeforeTask).text.toString()
        val loc = "Pakistan"
        val statusDetail = StatusDetails(4, scheduleName, date, time, work, timeBefore, loc)
        db.addActivity(statusDetail)

        val intent = Intent(this, MainActivity::class.java)
        finish()
        startActivity(intent)
    }

    fun btnDinnerSaveClicked(view: View) {
        val with = findViewById<TextView>(R.id.txtWithDinner).text.toString()
        val place = findViewById<TextView>(R.id.txtInDinner).text.toString()
        val time = findViewById<TextView>(R.id.txtTimeSetDinner).text.toString()
        val date = findViewById<TextView>(R.id.txtDateSetDinner).text.toString()
        val timeBefore = findViewById<TextView>(R.id.txtMinBeforeDinner).text.toString()
        val loc = "Pakistan"
        val statusDetail = StatusDetails(5, scheduleName,date, time, with, place, timeBefore, loc)
        db.addActivity(statusDetail)
        val intent = Intent(this, MainActivity::class.java)
        finish()
        startActivity(intent)
    }

    fun btnExtraSaveClicked(view: View) {
        val time = findViewById<TextView>(R.id.txtTimeSetExtra).text.toString()
        val date = findViewById<TextView>(R.id.txtDateSetExtra).text.toString()
        val work = findViewById<TextView>(R.id.txtWorkExtra).text.toString()
        val timeBefore = findViewById<TextView>(R.id.txtMinBeforeExtra).text.toString()
        val loc = "Pakistan"
        val statusDetail = StatusDetails(6, scheduleName,date,time,work,timeBefore,loc)
        db.addActivity(statusDetail)
        val intent = Intent(this, MainActivity::class.java)
        finish()
        startActivity(intent)
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
}
