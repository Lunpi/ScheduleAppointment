package com.example.scheduleappointment

import com.example.scheduleappointment.data.ProvidedSlots
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    //TODO: customize request here
    @GET("api/")
    fun getTimeSlots(@Query("date") date: Long): Call<ProvidedSlots>
}