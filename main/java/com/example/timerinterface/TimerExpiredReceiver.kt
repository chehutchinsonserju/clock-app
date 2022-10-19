package com.example.timerinterface

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.timerinterface.Util.NotificationUtil
import com.example.timerinterface.Util.PrefUtil

class TimerExpiredReceiver : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context, intent: Intent) {

        NotificationUtil.showTimerExpired(context)

        PrefUtil.setTimerState(MainActivity.Statetimer.Stop,context)
        PrefUtil.setAlarmSetTime(0,context)

    }
}