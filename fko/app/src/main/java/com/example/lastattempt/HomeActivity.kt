package com.example.lastattempt

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.DateFormat
import java.util.Calendar

class HomeActivity : AppCompatActivity(){

    private lateinit var calendar: Calendar
    private lateinit var textView: TextView

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        calendar = Calendar.getInstance()
        val currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.time)
        val currentTime = DateFormat.getTimeInstance().format(calendar.time)

        textView = findViewById(R.id.text_view_date)
        textView.text = currentDate

        textView = findViewById(R.id.text_view_time)
        textView.text = currentTime

        configureLinks()

    }
    private fun configureLinks() {
        val stopwatch = findViewById<FloatingActionButton>(R.id.stopwatchbtn)
        stopwatch.setOnClickListener {
            switchActivity("Stopwatch")
        }
        val timer = findViewById<FloatingActionButton>(R.id.timerbtn)
        timer.setOnClickListener{
            switchActivity("Timer")
        }
        val alarm = findViewById<FloatingActionButton>(R.id.alarmbtn)
        alarm.setOnClickListener {
            switchActivity("Alarm")
        }
    }

    private fun switchActivity(viewName: String) {

        when (viewName){
            "Stopwatch"->{
                val intent = Intent(this, StopwatchActivity::class.java)
                startActivity(intent)
            }
            "Timer" ->{
                val intent = Intent(this, TimerActivity::class.java)
                startActivity(intent)
            }
            "Alarm"->{
                val intent = Intent(this, AlarmActivity::class.java)
                startActivity(intent)
            }
            "Global"->{
                val intent = Intent(this, GlobalActivity::class.java)
                startActivity(intent)
            }
        }
    }
}