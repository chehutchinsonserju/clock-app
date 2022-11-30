package com.example.lastattempt

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

@Suppress("DEPRECATION")
class GlobalActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_global)

        val countryOffset = mapOf(
            "Afghanistan" to 270,
            "Aland Islands" to 120,
            "Albania" to 60,
            "Algeria" to 60,
            "American Samoa" to -660,
            "Andorra" to 60,
            "Angola" to 60,
            "Anguilla" to -240,
            "Antigua and Barbuda" to -240,
            "Argentina" to -180,
            "Australia EST" to 660,
            "Bahamas" to -300,
            "Barbados" to -240,
            "Belgium" to 60,
            "Belize" to -360,
            "Brazil" to -180,
            "British Virgin Islands" to -240,
            "Bulgaria" to 120,
            "Cambodia" to 420,
            "Cameroon" to 60,
            "Canada (EST)" to -300,
            "Cayman Islands" to -300,
            "Chad" to 60,
            "Chile (Continental)" to -180,
            "China" to 480,
            "Colombia" to -300,
            "Congo" to 60,
            "Costa Rica" to -360,
            "Cuba" to -300,
            "Denmark" to 60,
            "Dominica" to -240,
            "Ecuador" to -300,
            "Egypt" to -120,
            "Ethiopia" to 180,
            "Fiji" to 720,
            "Finland" to 120,
            "France" to 60,
            "Germany" to 60,
            "Ghana" to 0,
            "Greece" to 120,
            "Greenland (UTC)" to 0,
            "Grenada" to -240,
            "Guatemala" to -660,
            "Guyana" to -240,
            "Haiti" to -300,
            "Hong Kong" to 480,
            "Iceland" to 0,
            "India" to 330,
            "Iran" to 210,
            "Ireland" to 0,
            "Israel" to 120,
            "Italy" to 60,
            "Jamaica" to -300,
            "Japan" to 540,
            "Kenya" to 180,
            "Lebanon" to 120,
            "Liberia" to 0,
            "Libya" to 120,
            "Madagascar" to 180,
            "Malaysia" to 480,
            "Malta" to 60,
            "Mexico (EST)" to -360,
            "Mongolia" to 480,
            "Montserrat" to -240,
            "Morocco" to 60,
            "Mozambique" to 120,
            "Nepal" to 345,
            "Netherlands" to 60,
            "New Zealand" to 780,
            "Nicaragua" to -360,
            "Niger" to 60,
            "Nigeria" to 60,
            "North Korea" to 540,
            "Norway" to 60,
            "Pakistan" to 300,
            "Panama" to -300,
            "Peru" to -300,
            "Philippines" to 480,
            "Poland" to 60,
            "Portugal (WET)" to -60,
            "Puerto Rico" to -240,
            "Romania" to 120,
            "Russia (Moscow)" to 180,
            "Saint Kitts and Nevis" to -240,
            "Saint Lucia" to -240,
            "Saint Vincent and the Grenadines" to -240,
            "Saudi Arabia" to 180,
            "Singapore" to 480,
            "Slovakia" to 60,
            "South Africa" to 120,
            "South Korea" to 640,
            "Spain (CET)" to 60,
            "Sri Lanka" to 330,
            "Sudan" to 120,
            "Suriname" to -180,
            "Sweden" to 60,
            "Switzerland" to 60,
            "Syria" to 180,
            "Taiwan" to 480,
            "Thailand" to 420,
            "Togo" to 0,
            "Trinidad and Tobago" to -240,
            "Turkey" to 180,
            "Uganda" to 180,
            "Ukraine" to 120,
            "United Kingdom" to 0,
            "United States (CST)" to -360,
            "United States (EST)" to -300,
            "Venezuela" to -240,
            "Vietnam" to 420,
            "Western Sahara" to 60,
            "Yemen" to 180,
            "Zambia" to 120,
            "Zimbabwe" to 120
        )

        val countries = countryOffset.keys.toTypedArray()
        configureButtons(countries,countryOffset)
        configureLocalTime(countryOffset)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun configureLocalTime(countryOffset: Map<String,Int>) {
        val time = OffsetDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
        val localTime = findViewById<TextView>(R.id.localTime)
        localTime.text = time
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun configureButtons(countries: Array<String>, countryOffset: Map<String, Int>) {
        val selectContinent = findViewById<Button>(R.id.selectContinent)
        val intTime = findViewById<TextView>(R.id.interTime)
        selectContinent.setOnClickListener {
            println("Continent Selected")
        }
        val selectCountry = findViewById<Button>(R.id.selectCountry)
        selectCountry.setOnClickListener {
            println("Country Selected")
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.searchable_spinner)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()

            val editText = dialog.findViewById<EditText>(R.id.editText)
            val listView = dialog.findViewById<ListView>(R.id.listView)

            val arrayAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,countries)
            listView.adapter = arrayAdapter

            editText.addTextChangedListener {
                arrayAdapter.filter.filter(editText.text)
            }

            listView.setOnItemClickListener { adapterView, view, i, l ->
                var country = arrayAdapter.getItem(i).toString()
                country = getDateTimeFormatted(countryOffset[country]!!.toLong(),"HH:mm")
                intTime.text = country
                dialog.dismiss()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDateTimeFormatted(minutesToAdd: Long, pattern: String): String {
        // get current time in UTC, no millis needed
        val nowInUtc = OffsetDateTime.now(ZoneOffset.UTC)
        // add some minutes to it
        val someMinutesLater = nowInUtc.plusMinutes(minutesToAdd)
        // return the result in the given pattern
        return someMinutesLater.format(DateTimeFormatter.ofPattern(pattern))
    }
}
