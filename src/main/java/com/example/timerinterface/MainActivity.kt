package com.example.timerinterface

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.timerinterface.Util.NotificationUtil
import com.example.timerinterface.Util.PrefUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_first.*
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {

        @RequiresApi(Build.VERSION_CODES.M)
        fun setAlarm(context: Context, nowSeconds: Long, secondsRemaining: Long): Long{
            val wakeUpTime = (nowSeconds + secondsRemaining) * 1000
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, TimerExpiredReceiver::class.java)
            val pendingIntent: PendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, wakeUpTime, pendingIntent)
            PrefUtil.setAlarmSetTime(nowSeconds, context)
            return wakeUpTime
        }


        @RequiresApi(Build.VERSION_CODES.M)
        fun removeAlarm(context: Context){
            val intent = Intent(context, TimerExpiredReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(pendingIntent)
            PrefUtil.setAlarmSetTime(0, context)
        }

        val nowSeconds: Long
            get() = Calendar.getInstance().timeInMillis / 1000
    }

    enum class Statetimer{
        Stop, Pause, Run
    }

    private lateinit var timer: CountDownTimer
    private var timerLengthSeconds: Long = 0
    private var timerState = Statetimer.Stop

    private var secondsRemaining: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setIcon(R.drawable.ic_timer)
        supportActionBar?.title = "      Timer"

        fab_starttimer.setOnClickListener{v ->
            startTimer()
            timerState =  Statetimer.Run
            updateButtons()
        }

        fab_pausetimer.setOnClickListener { v ->
            timer.cancel()
            timerState = Statetimer.Pause
            updateButtons()
        }

        fab_stoptimer.setOnClickListener { v ->
            timer.cancel()
            onTimerFinished()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume() {
        super.onResume()

        initTimer()

        removeAlarm(this)
        NotificationUtil.hideTimerNotification(this)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onPause() {
        super.onPause()

        if (timerState == Statetimer.Run){
            timer.cancel()
            val wakeUpTime = setAlarm(this, nowSeconds, secondsRemaining)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                NotificationUtil.showTimerRunning(this, wakeUpTime)
            }
        }
        else if (timerState == Statetimer.Pause){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                NotificationUtil.showTimerPaused(this)
            }
        }

        PrefUtil.setPreviousTimerLengthSeconds(timerLengthSeconds, this)
        PrefUtil.setSecondsRemaining(secondsRemaining, this)
        PrefUtil.setTimerState(timerState, this)
    }

    private fun initTimer(){
        timerState = PrefUtil.getTimerState(this)


        if (timerState == Statetimer.Stop)
            setNewTimerLength()
        else
            setPreviousTimerLength()

        secondsRemaining = if (timerState == Statetimer.Run || timerState == Statetimer.Pause)
            PrefUtil.getSecondsRemaining(this)
        else
            timerLengthSeconds

        val alarmSetTime = PrefUtil.getAlarmSetTime(this)
        if (alarmSetTime > 0)
            secondsRemaining -= nowSeconds - alarmSetTime

        if (secondsRemaining <= 0)
            onTimerFinished()
        else if (timerState == Statetimer.Run)
            startTimer()

        updateButtons()
        updateCdownUI()
    }

    private fun onTimerFinished(){
        timerState = Statetimer.Stop

        setNewTimerLength()

        cdown_pbar.progress = 0

        PrefUtil.setSecondsRemaining(timerLengthSeconds, this)
        secondsRemaining = timerLengthSeconds

        updateButtons()
        updateCdownUI()
    }

    private fun startTimer(){
        timerState = Statetimer.Run

        timer = object : CountDownTimer(secondsRemaining * 1000, 1000) {
            override fun onFinish() = onTimerFinished()

            override fun onTick(millisUntilFinished: Long) {
                secondsRemaining = millisUntilFinished / 1000
                updateCdownUI()
            }
        }.start()
    }

    private fun setNewTimerLength(){
        val lengthInMinutes = PrefUtil.getTimerLength(this)
        timerLengthSeconds = (lengthInMinutes * 60L)
        cdown_pbar.max = timerLengthSeconds.toInt()
    }

    private fun setPreviousTimerLength(){
        timerLengthSeconds = PrefUtil.getPreviousTimerLengthSeconds(this)
        cdown_pbar.max = timerLengthSeconds.toInt()
    }

    @SuppressLint("SetTextI18n")
    private fun updateCdownUI(){
        val minutesUntilFinished = secondsRemaining / 60
        val secondsInMinuteUntilFinished = secondsRemaining - minutesUntilFinished * 60
        val secondsStr = secondsInMinuteUntilFinished.toString()
        textview_cdown.text = "$minutesUntilFinished:${if (secondsStr.length == 2) secondsStr else "0" + secondsStr}"
        cdown_pbar.progress = (timerLengthSeconds - secondsRemaining).toInt()
    }

    private fun updateButtons(){
        when (timerState) {
            Statetimer.Run ->{
                fab_starttimer.isEnabled = false
                fab_pausetimer.isEnabled = true
                fab_stoptimer.isEnabled = true
            }
            Statetimer.Stop -> {
                fab_starttimer.isEnabled = true
                fab_pausetimer.isEnabled = false
                fab_stoptimer.isEnabled = false
            }
            Statetimer.Pause -> {
                fab_starttimer.isEnabled = true
                fab_pausetimer.isEnabled = false
                fab_stoptimer.isEnabled = true
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


}