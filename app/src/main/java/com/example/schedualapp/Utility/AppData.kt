package com.example.schedualapp.Utility

import android.content.Context

class AppData (context: Context){
    val SHAREDPREF_NAME = "SharedPrefs"
    val PREFS_COUNT = "Count"
    val prefrence = context.getSharedPreferences(SHAREDPREF_NAME,Context.MODE_PRIVATE)

    fun getCount():Int{
        return prefrence.getInt(PREFS_COUNT,0)
    }

    fun setCount(count:Int){
        val editor = prefrence.edit()
        editor.putInt(PREFS_COUNT,count)
        editor.apply()
    }
}