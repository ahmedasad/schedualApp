package com.example.schedualapp.Controller

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.schedualapp.Adapter.MainAdapter
import com.example.schedualapp.Model.DataHelper
import com.example.schedualapp.Model.StatusDetails
import com.example.schedualapp.R
import com.example.schedualapp.Utility.DataService

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var adapter: MainAdapter
    lateinit var db: DataHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = DataHelper(this)
        refreshList()


        val layoutManager = LinearLayoutManager(this)
        statusList.layoutManager = layoutManager

        fab.setOnClickListener { view ->
            val intent = Intent(this, MenuActivity::class.java)
            finish()
            startActivity(intent)
        }
    }
    fun btnClicked(view: View){
        db.deleteDB(this)
    }

    private fun refreshList(){
        var listactivity: List<StatusDetails> = ArrayList<StatusDetails>()
        listactivity = db.allActivityList
        adapter = MainAdapter(this, listactivity)
        statusList.adapter = adapter
        adapter.notifyDataSetChanged()
    }

}


