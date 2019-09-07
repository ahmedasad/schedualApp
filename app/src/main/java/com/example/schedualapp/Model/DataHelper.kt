package com.example.schedualapp.Model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class DataHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VER) {

    companion object {
        private val DATABASE_VER = 1
        private val DATABASE_NAME = "data.db"

        //Table

        private val TABLE_NAME = "schedule"
        private val COL_ID = "Id"
        private val COL_TITLE = "_Title"
        private val COL_FROM = "_From"
        private val COL_TO = "_To"
        private val COL_WITH = "_With"
        private val COL_PLACE = "_Place"
        private val COL_EXTRANOTE = "_ExtraNote"
        private val COL_TIME = "_Time"
        private val COL_DATE = "_Date"
        private val COL_TIME_BEFORE = "_TimeBefore"
        private val COL_LOCATION = "_Location"


    }

    override fun onCreate(db: SQLiteDatabase?) {

        val CREATE_TABLE_QUERY = ("CREATE TABLE $TABLE_NAME ( $COL_ID INTEGER PRIMARY KEY, " +
                "$COL_TITLE TEXT, $COL_DATE TEXT,$COL_TIME TEXT,$COL_FROM TEXT,$COL_TO TEXT,$COL_WITH " +
                "TEXT,$COL_PLACE TEXT,$COL_EXTRANOTE TEXT,$COL_TIME_BEFORE TEXT, $COL_LOCATION TEXT)")
        db?.execSQL(CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db!!)
    }

    val allActivityList: List<StatusDetails>
        get() {
            val listActivity = ArrayList<StatusDetails>()
            val selectQuery = "SELECT * FROM $TABLE_NAME"
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQuery, null)

            if (cursor.moveToFirst()) {

                do {
                    val activity = StatusDetails()
                    activity.id = cursor.getInt(cursor.getColumnIndex(COL_ID))
                    activity.title = cursor.getString(cursor.getColumnIndex(COL_TITLE))
                    activity.date = cursor.getString(cursor.getColumnIndex(COL_DATE))
                    activity.time = cursor.getString(cursor.getColumnIndex(COL_TIME))
                    activity.from = cursor.getString(cursor.getColumnIndex(COL_FROM))
                    activity.to = cursor.getString(cursor.getColumnIndex(COL_TO))
                    activity.with = cursor.getString(cursor.getColumnIndex(COL_WITH))
                    activity.place = cursor.getString(cursor.getColumnIndex(COL_PLACE))
                    activity.timeBefore = cursor.getString(cursor.getColumnIndex(COL_TIME_BEFORE))
                    activity.extraNote = cursor.getString(cursor.getColumnIndex(COL_EXTRANOTE))
                    activity.location = cursor.getString(cursor.getColumnIndex(COL_LOCATION))

                    listActivity.add(activity)
                } while (cursor.moveToNext())
            }
            db.close()
            return listActivity
        }
    fun addActivity(activity:StatusDetails){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_ID,activity.id)
        values.put(COL_TITLE,activity.title)
        values.put(COL_FROM,activity.from)
        values.put(COL_TO,activity.to)
        values.put(COL_WITH,activity.with)
        values.put(COL_PLACE,activity.place)
        values.put(COL_EXTRANOTE,activity.extraNote)
        values.put(COL_TIME,activity.time)
        values.put(COL_DATE,activity.date)
        values.put(COL_TIME_BEFORE,activity.timeBefore)
        values.put(COL_LOCATION,activity.location)
        println(activity.location)
        db.insert(TABLE_NAME,null,values)
        db.close()

    }
    fun updateActivity(activity:StatusDetails):Int{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_ID,activity.id)
        values.put(COL_TITLE,activity.title)
        values.put(COL_FROM,activity.from)
        values.put(COL_TO,activity.to)
        values.put(COL_WITH,activity.with)
        values.put(COL_PLACE,activity.place)
        values.put(COL_EXTRANOTE,activity.extraNote)
        values.put(COL_TIME,activity.time)
        values.put(COL_DATE,activity.date)
        values.put(COL_TIME_BEFORE,activity.timeBefore)
        values.put(COL_LOCATION,activity.location)

        return db.update(TABLE_NAME,values,"$COL_ID=?", arrayOf(activity.id.toString()))

    }
    fun deleteActivity(activity:StatusDetails){
        val db = this.writableDatabase

        db.delete(TABLE_NAME,"$COL_ID=?", arrayOf(activity.id.toString()))
        db.close()

    }

fun deleteDB(context: Context){

    val db = this.writableDatabase
    db?.execSQL("delete from $TABLE_NAME")
    Toast.makeText(context,"deleted",Toast.LENGTH_LONG).show()
}

}