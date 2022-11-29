package com.example.timerinterface

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.timerinterface.Util.NotificationUtil
import com.example.timerinterface.Util.PrefUtil

class TimerNotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            AppConstants.ACTION_STOP -> {
                MainActivity.removeAlarm(context)
                PrefUtil.setTimerState(MainActivity.Statetimer.Stop, context)
                NotificationUtil.hideTimerNotification(context)
            }

            AppConstants.ACTION_PAUSE -> {
                var secondsRemaining = PrefUtil.getSecondsRemaining(context)
                val alarmSetTime = PrefUtil.getAlarmSetTime(context)
                val nowSeconds = MainActivity.nowSeconds

                secondsRemaining -= nowSeconds - alarmSetTime
                PrefUtil.setSecondsRemaining(secondsRemaining, context)

                MainActivity.removeAlarm(context)
                PrefUtil.setTimerState(MainActivity.Statetimer.Pause, context)
                NotificationUtil.showTimerPaused(context)
            }

            AppConstants.ACTION_RESUME -> {
                val secondsRemaining = PrefUtil.getSecondsRemaining(context)
                val wakeUpTime =
                    MainActivity.setAlarm(context, MainActivity.nowSeconds, secondsRemaining)
                PrefUtil.setTimerState(MainActivity.Statetimer.Run, context)
                NotificationUtil.showTimerRunning(context, wakeUpTime)
            }

            AppConstants.ACTION_START -> {
                val minutesRemaining = PrefUtil.getTimerLength(context)
                val secondsRemaining = minutesRemaining * 60L
                val wakeUpTime =
                    MainActivity.setAlarm(context, MainActivity.nowSeconds, secondsRemaining)
                PrefUtil.setTimerState(MainActivity.Statetimer.Run, context)
                PrefUtil.setSecondsRemaining(secondsRemaining, context)
                NotificationUtil.showTimerRunning(context, wakeUpTime)
            }
        }
    }
}