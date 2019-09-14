package com.example.schedualapp.BackgroundServices

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import com.example.schedualapp.Controller.ShowDataFromNotification
import com.example.schedualapp.Model.StatusDetails
import java.text.SimpleDateFormat
import java.util.*
import com.example.schedualapp.Controller.MainActivity
import android.app.*
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.schedualapp.Utility.ACTIVITY_ID
import com.example.schedualapp.Utility.DataHelper

class JobServices : Service() {
    private val db = DataHelper(this)
    lateinit var player: MediaPlayer
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

//        player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI)
//        player.isLooping = true
//        player.start()
        Handler(Looper.getMainLooper()).post(object : Runnable {
            private val sdfTime = SimpleDateFormat("h:mm a")
            private val currenttime = sdfTime.format(Calendar.getInstance().time)
            private var timeCopy = currenttime
            override fun run() {
                val currenttime = sdfTime.format(Calendar.getInstance().time)
                if(!timeCopy.contentEquals(currenttime)){
                    getList()
                    println("getting List")
                    timeCopy = currenttime
                }

                Handler().postDelayed(this, 1000)
            }
        })

        return START_STICKY

    }

    override fun onDestroy() {
        super.onDestroy()

    }

    private fun getList() {
        val listactivity: List<StatusDetails>
        listactivity = db.allActivityList
        println("started getting List")
        for (i in 0 until listactivity.size) {
            val id = listactivity.get(i).id
            println("started getting List $id")
            val date = listactivity[i].date
            val time = listactivity[i].time
            compareDateAndTime(time, date, id)
        }
    }

    private fun compareDateAndTime(time: String, date: String, id: Int) {
        val sdfTime = SimpleDateFormat("h:mm a")
        val sdfDate = SimpleDateFormat("d MMM, yyyy")


        val currenttime = sdfTime.format(Calendar.getInstance().time)
        val currentdate = sdfDate.format(Calendar.getInstance().time)

        println("current $currentdate $currenttime $date $time")
        if (date.length == currentdate.length && time.contentEquals(currenttime)) {
            println("started creating notification")
            createNotification(id)
        } else {
            println("did not matched")
        }
    }

    private fun createNotification(id: Int):Int {
        val item = getItem(id)
        val resId = this.resources.getIdentifier("${item.title}","drawable",this.packageName)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationForApi26(item)
            return 0
        }

        val builder = NotificationCompat.Builder(this)
            .setContentTitle("Schedule App")
            .setSmallIcon(com.example.schedualapp.R.mipmap.ic_launcher)
            .setContentText("${item.title} need to do write now!")
        val ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        builder.setSound(ringtone)

        val notification = Intent(this, ShowDataFromNotification::class.java)
        notification.putExtra(ACTIVITY_ID,id.toString())
        val contentIntent = PendingIntent.getActivity(this, 0, notification, PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(contentIntent)

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(0, builder.build())


      return 0

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun notificationForApi26(item:StatusDetails){
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager




            val CHANNEL_ID = "my_channel_01"
            val name = "my_channel"
            val Description = "This is my channel"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            mChannel.description = Description
            mChannel.enableLights(true)
            mChannel.lightColor = Color.RED
            mChannel.enableVibration(true)
            mChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            mChannel.setShowBadge(false)
            notificationManager.createNotificationChannel(mChannel)


    val builder = NotificationCompat.Builder(this,CHANNEL_ID)
        .setContentTitle("Schedule App")
        .setSmallIcon(com.example.schedualapp.R.mipmap.ic_launcher)
        .setContentText("${item.title} need to do write now!")
    val ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    builder.setSound(ringtone)

    val resultIntent = Intent(this, ShowDataFromNotification::class.java)
        resultIntent.putExtra(ACTIVITY_ID,item.id.toString())
        val stackBuilder = TaskStackBuilder.create(this)
        stackBuilder.addParentStack(MainActivity::class.java)
        stackBuilder.addNextIntent(resultIntent)
        val resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)

        builder.setContentIntent(resultPendingIntent)

        notificationManager.notify(0, builder.build())


    }
    private fun getItem(id: Int): StatusDetails {
        var item: StatusDetails = db.allActivityList[0]
        val listactivity = db.allActivityList
        for (i in 0 until listactivity.size) {
            if (listactivity[i].id == id) {
                println("returning item")
                return listactivity[i]

            }
        }
        return item
    }
}