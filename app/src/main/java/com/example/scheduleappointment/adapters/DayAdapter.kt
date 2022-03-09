package com.example.scheduleappointment.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.scheduleappointment.DateTimeUtils
import com.example.scheduleappointment.R
import com.example.scheduleappointment.data.TimeSlot

class DayAdapter : RecyclerView.Adapter<DayViewHolder>() {

    var startDate = 0L
    // weekTimeSlots[0] for Sunday, weekTimeSlots[1] for Monday and so on
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
    private val container = itemView.findViewById<LinearLayout>(R.id.container_slot)

    fun bind(timestamp: Long, timeSlots: List<TimeSlot>) {
        day.apply {
            text = itemView.context.getString(
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
            setTextColor(ContextCompat.getColor(context,
                if (timestamp < DateTimeUtils.getTodayTimestamp()) R.color.disable else R.color.enable))
        }

        dayNumber.apply {
            text = DateTimeUtils.timestampToDay(timestamp)
            setTextColor(ContextCompat.getColor(context,
                if (timestamp < DateTimeUtils.getTodayTimestamp()) R.color.disable else R.color.text_color_primary))
        }

        container.apply {
            removeAllViews()
            timeSlots.forEach { slot ->
                val slotView = LayoutInflater.from(context).inflate(R.layout.list_item_time_slot, this, false).apply {
                    findViewById<TextView>(R.id.text_time).apply {
                        text = DateTimeUtils.timestampToHourMinute(slot.startTime)
                        setTextColor(ContextCompat.getColor(context, if (slot.available) R.color.enable else R.color.disable))
                    }
                }
                addView(slotView)
            }
        }
    }
}