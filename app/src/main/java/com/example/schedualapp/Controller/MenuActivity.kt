package com.example.schedualapp.Controller

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.schedualapp.R
import com.example.schedualapp.Utility.SCHEDULE_NAME

class MenuActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

    }



    fun travelClicked(view: View) {
        funOnClick("travel")
    }

    fun meetingClicked(view: View) {
        funOnClick("meeting")
    }

    fun callClicked(view: View) {
        funOnClick("call")
    }

    fun taskClicked(view: View) {
        funOnClick("task")
    }

    fun dinnerClicked(view: View) {
        funOnClick("dinner")
    }

    fun extraClicked(view: View) {
        funOnClick("extra")
    }

    fun funOnClick(item: String) {
        val intent = Intent(this, CreateItemActivity::class.java)
        intent.putExtra(SCHEDULE_NAME, item)
        finish()
        startActivity(intent)
    }

}