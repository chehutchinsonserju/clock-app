package com.example.timeronservice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.Time
import kotlin.math.roundToInt
import com.example.timeronservice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityMainBinding
    private var timerStarted = false
    private lateinit var serviceIntent: Intent
    private var time = 0.0

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStartStop.setOnClickListener{startStopTimer()}
        binding.btnReset.setOnClickListener{resetTimer()}

        serviceIntent = Intent(applicationContext, Stopwatch::class.java)
        registerReceiver(updateTime, IntentFilter(Stopwatch.TIMER_UPDATED))

    }

    private fun startStopTimer()
    {
        if(timerStarted)
            stopTimer()
        else
            startTimer()
    }
    private fun startTimer()
    {
        serviceIntent.putExtra(Stopwatch.TIME_EXTRA, time)
        startService(serviceIntent)
        binding.btnStartStop.text = "Stop"
        binding.btnStartStop.icon = getDrawable(R.drawable.pauseicon)
        timerStarted = true
    }
    private fun stopTimer()
    {
        serviceIntent.putExtra(Stopwatch.TIME_EXTRA, time)
        stopService(serviceIntent)
        binding.btnStartStop.text = "Start"
        binding.btnStartStop.icon = getDrawable(R.drawable.playicon)
        timerStarted = false
    }
    private fun resetTimer()
    {
        stopTimer()
        time = 0.0
        binding.time.text = getTimeStringFromDouble(time)


    }

    private val updateTime: BroadcastReceiver = object : BroadcastReceiver()
    {
        override fun onReceive(context: Context, intent: Intent)
        {
            time = intent.getDoubleExtra(Stopwatch.TIME_EXTRA, 0.0)
            binding.time.text = getTimeStringFromDouble(time)
        }
    }

    private fun getTimeStringFromDouble(time: Double): String
    {
        val resultInt = time.roundToInt()

        val hours = resultInt % 86400 / 3600
        val minutes = resultInt % 86400 % 3600 / 68
        val seconds = resultInt % 86400 % 3600 % 68

        return makeTimeString(hours, minutes, seconds)

    }

    private fun makeTimeString(hour: Int, min: Int, sec: Int): String = String.format("%02d:%02d:%02d", hour, min, sec)



}