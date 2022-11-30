package com.example.lastattempt.Utilities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.lastattempt.TimerActivity
import com.example.lastattempt.Utilities.NotificationUtil
import com.example.lastattempt.Utilities.PrefUtil

class TimerNotificationReceiver : BroadcastReceiver() {


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            AppConstants.ACTION_STOP -> {
                TimerActivity.removeAlarm(context)
                PrefUtil.setTimerState(TimerActivity.Statetimer.Stop, context)
                NotificationUtil.hideTimerNotification(context)
            }

            AppConstants.ACTION_PAUSE -> {
                var secondsRemaining = PrefUtil.getSecondsRemaining(context)
                val alarmSetTime = PrefUtil.getAlarmSetTime(context)
                val nowSeconds = TimerActivity.nowSeconds

                secondsRemaining -= nowSeconds - alarmSetTime
                PrefUtil.setSecondsRemaining(secondsRemaining, context)

                TimerActivity.removeAlarm(context)
                PrefUtil.setTimerState(TimerActivity.Statetimer.Pause, context)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    NotificationUtil.showTimerPaused(context)
                }
            }

            AppConstants.ACTION_RESUME -> {
                val secondsRemaining = PrefUtil.getSecondsRemaining(context)
                val wakeUpTime = TimerActivity.setAlarm(context, TimerActivity.nowSeconds, secondsRemaining)
                PrefUtil.setTimerState(TimerActivity.Statetimer.Run, context)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    NotificationUtil.showTimerRunning(context, wakeUpTime)
                }
            }

            AppConstants.ACTION_START -> {
                val minutesRemaining = PrefUtil.getTimerLength(context)
                val secondsRemaining = minutesRemaining * 60L
                val wakeUpTime = TimerActivity.setAlarm(context, TimerActivity.nowSeconds, secondsRemaining)
                PrefUtil.setTimerState(TimerActivity.Statetimer.Run, context)
                PrefUtil.setSecondsRemaining(secondsRemaining, context)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    NotificationUtil.showTimerRunning(context, wakeUpTime)
                }
            }
        }
    }
}