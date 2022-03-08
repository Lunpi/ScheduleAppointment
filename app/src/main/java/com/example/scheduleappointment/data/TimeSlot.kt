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

data class TestData(
    @SerializedName("data")
    val data: List<User>,
    
    @SerializedName("total")
    val total: Int
)

data class User(
    @SerializedName("id") val id: Int,
    @SerializedName("first_name") val firstName: String,
    val test: Boolean
)