package com.example.alarmmanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast


class MyBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent!!.action.equals("com.example.alarmmanager")){
            val b=intent.extras
            // Toast.makeText(context,b.getString("message"),Toast.LENGTH_LONG).show()
            val notifyMe=Notifications()
            notifyMe.Notify(context!!, b!!.getString("message")!!, 10)
        }
        else // Toast.makeText(context,b.getString("message"),Toast.LENGTH_LONG).show()
        {
            if(intent.action.equals("android.intent.action.BOOT_COMPLETED")){
                val saveData=SaveData(context!!)
                saveData.setAlarm()
            }
        }
    }
}