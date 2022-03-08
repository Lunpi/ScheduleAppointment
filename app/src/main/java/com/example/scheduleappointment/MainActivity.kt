package com.example.scheduleappointment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scheduleappointment.adapters.DayAdapter
import com.example.scheduleappointment.data.TimeSlot
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ScheduleViewModel
    private lateinit var dayList: RecyclerView
    private val dayAdapter = DayAdapter()
    private var sunday = DateTimeUtils.getSundayTimestamp()
    private var saturday = sunday + DateTimeUtils.day * 6

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[ScheduleViewModel::class.java]

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

        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)

        dayList = findViewById<RecyclerView>(R.id.container_day).apply {
            adapter = dayAdapter
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        }

        viewModel.timeSlots.observe(this) { slots ->
            splitSlotsByDay(slots)
        }

        viewModel.processing.observe(this) { processing ->
            progressBar.visibility = if (processing) View.VISIBLE else View.GONE
        }
    }

    override fun onStart() {
        super.onStart()
        // user might change timezone at settings
        syncDate()
        updateDayAdapter()
    }

    private fun checkPreviousWeekEnable() {
        findViewById<ImageView>(R.id.image_left_arrow).apply {
            isEnabled = !(sunday < DateTimeUtils.getTodayTimestamp())
            DrawableCompat.setTint(drawable,
                ContextCompat.getColor(this@MainActivity, if (isEnabled) R.color.button_enable else R.color.button_disable))
        }
    }

    private fun syncDate() {
        sunday = DateTimeUtils.getSundayTimestamp()
        saturday = sunday + DateTimeUtils.day * 6
        findViewById<TextView>(R.id.text_date).apply {
            text = getString(R.string.date_interval, DateTimeUtils.timestampToYearDate(sunday), DateTimeUtils.timestampToDate(saturday))
        }
        findViewById<TextView>(R.id.text_date_note).apply {
            text = getString(R.string.date_note, TimeZone.getDefault().getDisplayName(false, TimeZone.SHORT))
        }
    }

    private fun updateDayAdapter() {
        dayAdapter.startDate = sunday
        viewModel.updateTimeSlots(sunday)
    }

    private fun splitSlotsByDay(slots: List<TimeSlot>) {
        dayAdapter.apply {
            for (i in weekTimeSlots.indices) {
                val dayStartTime = sunday + DateTimeUtils.day * i
                val dayEndTime = dayStartTime + DateTimeUtils.day
                val filtered = slots.filter { item -> item.startTime >= dayStartTime && item.endTime <= dayEndTime }
                weekTimeSlots[i] = filtered
            }
            notifyDataSetChanged()
        }
    }
}