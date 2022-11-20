package com.example.lastattempt.fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.lastattempt.R
import com.example.lastattempt.databinding.FragmentStopwatchBinding
import kotlin.math.roundToInt

class StopwatchMain : Fragment(R.layout.fragment_stopwatch)
{
    private var _binding : FragmentStopwatchBinding? = null
    private val binding get() = _binding!!
    private var timerStarted = false
    private lateinit var serviceIntent: Intent
    private var time = 0.0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStopwatchBinding.inflate(inflater,container,false)

        binding.btnStartStop.setOnClickListener{startStopTimer()}
        binding.btnReset.setOnClickListener{resetTimer()}

        serviceIntent = Intent(activity, Stopwatch::class.java)
        activity?.registerReceiver(updateTime, IntentFilter(Stopwatch.TIMER_UPDATED))
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
        activity?.startService(serviceIntent)
        binding.btnStartStop.text = getString(R.string.stop)
        binding.btnStartStop.icon = ResourcesCompat.getDrawable(requireActivity().resources,R.drawable.pauseicon,null)
        timerStarted = true
    }
    private fun stopTimer()
    {
        serviceIntent.putExtra(Stopwatch.TIME_EXTRA, time)
        activity?.stopService(serviceIntent)
        binding.btnStartStop.text = getString(R.string.start)
        binding.btnStartStop.icon = ResourcesCompat.getDrawable(requireActivity().resources,R.drawable.playicon,null)
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