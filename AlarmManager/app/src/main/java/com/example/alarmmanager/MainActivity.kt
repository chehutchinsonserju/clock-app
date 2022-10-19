package com.example.alarmmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val saveData = SaveData(applicationContext)
        val tvShowTime.text = saveData.getHour().toString() + ":" + saveData.getMinute().toString()
    }

    fun buSetTime(view: View) {
        val popTime = PopTime()
        val fm = supportFragmentManager
        popTime.show(fm, "Select time")
    }

    fun setTime(Hours: Int, Minute: Int) {
        val tvShowTime.text = "$Hours : $Minute"
    }
        val saveData = SaveData(applicationContext
        saveData.SaveData(Hours, Minute)
        saveData.setAlarm()
    }
}