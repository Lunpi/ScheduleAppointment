package com.example.scheduleappointment.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.scheduleappointment.DateTimeUtils
import com.example.scheduleappointment.R
import com.example.scheduleappointment.data.TimeSlot

class TimeAdapter : RecyclerView.Adapter<TimeViewHolder>() {

    val slots = ArrayList<TimeSlot>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder = TimeViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.list_item_time_slot, parent, false))

    override fun getItemCount() = slots.size

    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {
        holder.bind(slots[position])
    }
}

class TimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val time = itemView.findViewById<TextView>(R.id.text_time)

    fun bind(slot: TimeSlot) {
        time.text = DateTimeUtils.timestampToHourMinute(slot.startTime)
    }
}