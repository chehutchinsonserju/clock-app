package com.example.lastattempt.Utilities

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.lastattempt.R

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val i = Intent(context,DestinationActivity::class.java)
        intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(context,0,i,0)

        val builder = NotificationCompat.Builder(context!!,"Alarm")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Chronos Alarm")
            .setContentText("Time Has Elapsed")
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setVibrate(longArrayOf(500,500,500))
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(123,builder.build())

    }
}