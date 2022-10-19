package com.example.alarmmanager

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

class  Notifications {

    val NOTIFIYTAG="new request"
    fun notify(context: Context,message:String,number:Int){
        val intent=Intent(context,MainActivity::class.java)
        val builder=NotificationCompat.Builder(context)
            .setDefaults(Notification.DEFAULT_ALL)
            .setContentTitle("New request")
            .setContentText(message)
            .setNumber(number)
            .setSmallIcon(androidx.loader.R.drawable.notification_icon_background)
            .setContentIntent(
                getActivity(context
                ,0,intent, FLAG_UPDATE_CURRENT
                )
            )
            .setAutoCancel(true)

        val nm=context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.ECLAIR) {
            nm.notify(NOTIFIYTAG, 0, builder.build())
        }else{
            nm.notify(NOTIFIYTAG.hashCode(), builder.build())
        }
    }
}