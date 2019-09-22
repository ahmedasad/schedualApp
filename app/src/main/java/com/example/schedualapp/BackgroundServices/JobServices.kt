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
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import androidx.core.os.postDelayed
import com.example.schedualapp.Utility.ACTIVITY_ID
import com.example.schedualapp.Utility.DataHelper


class JobServices : Service() {
    private val db = DataHelper(this)

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
                if (!timeCopy.contentEquals(currenttime)) {
                    getList()
                    println("getting List")
                    timeCopy = currenttime
                }

                Handler().postDelayed(this, 1000)
            }
        })
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            foreGroundNotification()
        }

//        Handler(Looper.getMainLooper()).post(object : Runnable {
//            override fun run() {
//                onDestroy()
//                Handler().postDelayed(this, 10000)
//            }
//        })

        return START_STICKY

    }

    override fun onDestroy() {
        if (Build.VERSION.SDK_INT >= 26) {
            ContextCompat.startForegroundService(this, Intent(this, JobServices::class.java))
        } else {
            startService(Intent(this, JobServices::class.java))
        }
        super.onDestroy()
    }

    val ID = 323

    private fun foreGroundNotification() {
        val CHANNEL_ID =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel("my_service", "My Background Service")
            } else {
                // If earlier version channel ID is not used
                // https://developer.android.com/reference/android/support/v4/app/NotificationCompat.Builder.html#NotificationCompat.Builder(android.content.Context)
                ""
            }

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)

            .setPriority(NotificationCompat.PRIORITY_MAX)
            .build()
        startForeground(ID, notification)
        START_STICKY

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel(channelId: String, channelName: String): String {
        val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_NONE)
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(channel)
        return channelId
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


        val alteredtime = time.split(" ")[0] +" "+time.split(" ")[1].toLowerCase()
        var aleredcurrenttime = currenttime.split(" ")[0] +" "+currenttime.split(" ")[1].toLowerCase()

        val curtime = aleredcurrenttime.split(" ")[0].split(":")[0]
        if(curtime.toInt()>12)aleredcurrenttime=(curtime.toInt()-12).toString()+currenttime.split(" ")[0].split(":")[1]+" "+currenttime.split(" ")[1].toLowerCase()


        println("current $currentdate $currenttime settled $date $time")
        if (date.contentEquals(currentdate) && alteredtime.contentEquals(aleredcurrenttime)) {
            println("started creating notification")
            createNotification(id)
        } else {
            println("did not matched")
        }
    }

    private fun createNotification(id: Int): Int {
        val item = getItem(id)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationForApi26(item)
            return 0
        }

        val builder = NotificationCompat.Builder(this)
            .setContentTitle("Schedule App")
            .setSmallIcon(com.example.schedualapp.R.mipmap.ic_launcher)
            .setContentText("${item.title} need to do right now!")
            .setAutoCancel(true)
        val ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        builder.setSound(ringtone)

        val notification = Intent(this, ShowDataFromNotification::class.java)
        notification.putExtra(ACTIVITY_ID, id.toString())
        val contentIntent = PendingIntent.getActivity(this, 0, notification, PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(contentIntent)

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(0, builder.build())


        return 0

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun notificationForApi26(item: StatusDetails) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        val CHANNEL_ID = "Alert"
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


        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Schedule App")
            .setSmallIcon(com.example.schedualapp.R.mipmap.ic_launcher)
            .setContentText("${item.title} need to do right now!")
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setAutoCancel(true)
        val ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        builder.setSound(ringtone)

        val resultIntent = Intent(this, ShowDataFromNotification::class.java)
        resultIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        resultIntent.putExtra(ACTIVITY_ID, item.id.toString())
        val stackBuilder = TaskStackBuilder.create(this)
        stackBuilder.addParentStack(MainActivity::class.java)
        stackBuilder.addNextIntent(resultIntent)
        val resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)

        builder.setContentIntent(resultPendingIntent)
        notificationManager.notify(12, builder.build())

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

    private fun restartService(){

    }
}