package com.example.scheduleappointment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scheduleappointment.adapters.DayAdapter
import com.example.scheduleappointment.adapters.TimeAdapter
import java.util.*

class MainActivity : AppCompatActivity() {
    
    private lateinit var dayList: RecyclerView
    private val dayAdapter = DayAdapter()
    private var sunday = DateTimeUtils.getSundayTimestamp()
    private var saturday = sunday + DateTimeUtils.day * 6
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dateInterval = findViewById<TextView>(R.id.text_date).apply {
            text = getString(R.string.date_interval, DateTimeUtils.timestampToYearDate(sunday), DateTimeUtils.timestampToDate(saturday))
        }
        
        findViewById<TextView>(R.id.text_date_note).apply {
            text = getString(R.string.date_note, TimeZone.getDefault().getDisplayName(false, TimeZone.SHORT))
        }
        
        findViewById<ImageView>(R.id.image_left_arrow).apply {
            setOnClickListener {
                sunday -= DateTimeUtils.week
                saturday -= DateTimeUtils.week
                dateInterval.text = getString(R.string.date_interval, DateTimeUtils.timestampToYearDate(sunday), DateTimeUtils.timestampToDate(saturday))
                checkPreviousWeekEnable()
                updateDayAdapter()
            }
        }
        
        findViewById<ImageView>(R.id.image_right_arrow).apply {
            setOnClickListener {
                sunday += DateTimeUtils.week
                saturday += DateTimeUtils.week
                dateInterval.text = getString(R.string.date_interval, DateTimeUtils.timestampToYearDate(sunday), DateTimeUtils.timestampToDate(saturday))
                checkPreviousWeekEnable()
                updateDayAdapter()
            }
        }

        dayList = findViewById<RecyclerView>(R.id.container_day).apply {
            updateDayAdapter()
            adapter = dayAdapter
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }
    
    private fun checkPreviousWeekEnable() {
        findViewById<ImageView>(R.id.image_left_arrow).apply {
            isEnabled = !(sunday < DateTimeUtils.getTodayTimestamp())
            DrawableCompat.setTint(drawable,
                ContextCompat.getColor(this@MainActivity, if (isEnabled) R.color.button_enable else R.color.button_disable))
        }
    }
    
    private fun updateDayAdapter() {
        dayAdapter.apply {
            startDate = sunday
            notifyItemRangeChanged(0, itemCount)
        }
    }
}