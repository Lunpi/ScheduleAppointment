package com.example.scheduleappointment.data

import com.google.gson.annotations.SerializedName

data class TimeSlot(
    val startTime: Long,
    val endTime: Long,
    val available: Boolean
)

data class ProvidedSlots(
    @SerializedName("available") val available: List<JsSlot>,
    @SerializedName("booked") val booked: List<JsSlot>
)
data class JsSlot(
    @SerializedName("start") val start: String,
    @SerializedName("end") val end: String
)