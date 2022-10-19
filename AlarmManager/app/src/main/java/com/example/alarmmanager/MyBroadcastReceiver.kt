package com.example.alarmmanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent



class MyBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent!!.action.equals("com.example.alarmmanager")){
            val b=intent.extras
            val notifyMe=Notifications()
            notifyMe.notify(context!!, b!!.getString("message")!!, 10)
        }
        else
        {
            if(intent.action.equals("android.intent.action.BOOT_COMPLETED")){
                val saveData=SaveData(context!!)
                saveData.setAlarm()
            }
        }
    }
}