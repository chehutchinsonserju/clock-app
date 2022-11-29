package com.example.clockproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextClock

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var textClock = findViewById<TextClock>(R.id.textclockid)
        var textdate = findViewById<TextClock>(R.id.textdateid)
    }
}

