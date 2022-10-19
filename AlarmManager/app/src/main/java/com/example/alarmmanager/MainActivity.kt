package com.example.alarmmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import  kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val saveData = SaveData(applicationContext)
        "${saveData.getHour()}:${saveData.getMinute()}".also { tvShowTime.text = it }
    }

    fun buSetTime(view: View) {
        val popTime = PopTime()
        val fm = supportFragmentManager
        popTime.show(fm, "Select time")
    }

    fun setTime(Hours: Int, Minute: Int) {
        tvShowTime.text = "$Hours : $Minute"
        val saveData = SaveData(applicationContext)
        saveData.saveData(Hours, Minute)
        saveData.setAlarm()
    }
}

