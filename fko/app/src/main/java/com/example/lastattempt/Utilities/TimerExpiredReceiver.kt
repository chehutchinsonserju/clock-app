package com.example.lastattempt.Utilities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.lastattempt.TimerActivity
import com.example.lastattempt.Utilities.NotificationUtil
import com.example.lastattempt.Utilities.PrefUtil

class TimerExpiredReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NotificationUtil.showTimerExpired(context)
        }

        PrefUtil.setTimerState(TimerActivity.Statetimer.Stop,context)
        PrefUtil.setAlarmSetTime(0,context)

    }
}