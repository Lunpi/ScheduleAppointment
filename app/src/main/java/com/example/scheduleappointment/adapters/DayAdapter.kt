package com.example.scheduleappointment.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.scheduleappointment.DateTimeUtils
import com.example.scheduleappointment.R
import com.example.scheduleappointment.data.TimeSlot

class DayAdapter : RecyclerView.Adapter<DayViewHolder>() {

    var startDate = 0L
    val weekTimeSlots = Array<List<TimeSlot>>(7) { emptyList() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder = DayViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.list_item_day, parent, false))

    override fun getItemCount() = 7

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        holder.bind(startDate + DateTimeUtils.day * position, weekTimeSlots[position])
    }
}

class DayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val day = itemView.findViewById<TextView>(R.id.text_day)
    private val dayNumber = itemView.findViewById<TextView>(R.id.text_day_no)
    private val timeSlotList = itemView.findViewById<RecyclerView>(R.id.container_time_slot)
    private val timeAdapter = TimeAdapter()

    fun bind(timestamp: Long, timeSlots: List<TimeSlot>) {
        day.text = itemView.context.getString(
            when (layoutPosition) {
                0 -> R.string.sunday
                1 -> R.string.monday
                2 -> R.string.tuesday
                3 -> R.string.wednesday
                4 -> R.string.thursday
                5 -> R.string.friday
                else -> R.string.saturday
            }
        )
        dayNumber.text = DateTimeUtils.timestampToDay(timestamp)

        timeAdapter.apply {
            slots.clear()
            slots.addAll(timeSlots)
            notifyDataSetChanged()
        }

        timeSlotList.apply {
            adapter = timeAdapter
        }
    }
}