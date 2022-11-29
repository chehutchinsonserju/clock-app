package com.example.lastattempt

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.lastattempt.Utilities.Stopwatch
import com.example.lastattempt.databinding.ActivityStopwatchBinding
import kotlin.math.roundToInt

class StopwatchActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityStopwatchBinding
    private var timerStarted = false
    private lateinit var serviceIntent: Intent
    private var time = 0.0

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityStopwatchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStartStop.setOnClickListener{startStopTimer()}
        binding.btnReset.setOnClickListener{resetTimer()}

        serviceIntent = Intent(applicationContext, Stopwatch::class.java)
        registerReceiver(updateTime, IntentFilter(Stopwatch.TIMER_UPDATED))

        configureLinks()
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
        binding.btnStartStop.text = getString(R.string.stop)
        binding.btnStartStop.icon = getDrawable(R.drawable.pauseicon)
        timerStarted = true
    }
    private fun stopTimer()
    {
        serviceIntent.putExtra(Stopwatch.TIME_EXTRA, time)
        stopService(serviceIntent)
        binding.btnStartStop.text = getString(R.string.start)
        binding.btnStartStop.icon = getDrawable(R.drawable.playicon)
        timerStarted = false
    }
    private fun resetTimer()
    {
        stopTimer()
        time = 0.0
        binding.timeTV.text = getTimeStringFromDouble(time)


    }
    private val updateTime: BroadcastReceiver = object : BroadcastReceiver()
    {
        override fun onReceive(context: Context, intent: Intent)
        {
            time = intent.getDoubleExtra(Stopwatch.TIME_EXTRA, 0.0)
            binding.timeTV.text = getTimeStringFromDouble(time)
        }
    }
    private fun getTimeStringFromDouble(time: Double): String
    {
        val resultInt = time.roundToInt()

        val hours = resultInt % 86400 / 3600
        val minutes = resultInt % 86400 % 3600 / 60
        val seconds = resultInt % 86400 % 3600 % 60

        return makeTimeString(hours, minutes, seconds)
    }
    private fun makeTimeString(hour: Int, min: Int, sec: Int): String = String.format("%02d:%02d:%02d", hour, min, sec)

    private fun configureLinks() {
        val clock = findViewById<Button>(R.id.clockbtn)
        clock.setOnClickListener {
            switchActivity("Timer")
        }
        val stopwatch = findViewById<Button>(R.id.stopwatchbtn)
        stopwatch.setOnClickListener{
            switchActivity("Stopwatch")
        }
}

    private fun switchActivity(viewName: String) {

        when (viewName){
            "Timer" ->{
                val intent = Intent(this, TimerActivity::class.java)
                startActivity(intent)
            }
            "Stopwatch"->{
                val intent = Intent(this, Stopwatch::class.java)
                startActivity(intent)
            }
        }
    }
}
