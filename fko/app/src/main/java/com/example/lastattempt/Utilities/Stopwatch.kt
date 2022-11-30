package com.example.lastattempt.Utilities

import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.util.* //needed for Timer

class Stopwatch : Service()
{
    override fun onBind(p0: Intent?): IBinder? = null //stop error


    private val timer = Timer()

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int
    {
        val time = intent.getDoubleExtra(TIME_EXTRA, 0.0)
        timer.scheduleAtFixedRate(TimeTask(time), 0, 1000) //100 milliseconds
        return START_NOT_STICKY
    }

    override fun onDestroy() //when service is destroyed stop timer
    {
        timer.cancel()
        super.onDestroy()
    }

    private inner class TimeTask(private var time: Double) : TimerTask() //extends TimerTask
    {
        override fun run()
        {
            val intent = Intent(TIMER_UPDATED)
            time++
            intent.putExtra(TIME_EXTRA, time)
            sendBroadcast(intent)
        }
    }

    companion object
    {
        const val TIMER_UPDATED = "stopwatchUpdated"
        const val TIME_EXTRA = "timeExtra"
    }
}