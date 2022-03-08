package com.example.scheduleappointment

import com.example.scheduleappointment.data.ProvidedSlots
import com.example.scheduleappointment.data.TestData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/users")
    fun getTestData(@Query("page") page: Int): Call<TestData>

    @GET("api/")
    fun getTimeSlots(@Query("date") data: Long): Call<ProvidedSlots>
}