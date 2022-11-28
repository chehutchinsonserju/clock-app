package com.example.timerinterface

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.timerinterface.Util.NotificationUtil
import com.example.timerinterface.Util.PrefUtil

class TimerExpiredReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NotificationUtil.showTimerExpired(context)
        }

        PrefUtil.setTimerState(MainActivity.Statetimer.Stop,context)
        PrefUtil.setAlarmSetTime(0,context)

    }
}