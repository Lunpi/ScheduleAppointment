package com.example.scheduleappointment

import com.example.scheduleappointment.data.*
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class ScheduleRepository {

    private val retrofit = Retrofit.Builder()
        .baseUrl(API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(ApiService::class.java)

    suspend fun query(date: Long): List<TimeSlot> {
        return suspendCancellableCoroutine {
            service.getTimeSlots(date).enqueue(object : Callback<ProvidedSlots> {

                override fun onResponse(call: Call<ProvidedSlots>?, response: Response<ProvidedSlots>?) {
                    val timeSlots = ArrayList<TimeSlot>()
                    response?.body()?.available?.let { slots ->
                        slots.forEach { js ->
                            val formattedSlot = TimeSlot(
                                startTime = DateTimeUtils.jsFormatToTimestamp(js.start),
                                endTime = DateTimeUtils.jsFormatToTimestamp(js.end),
                                available = true
                            )
                            timeSlots.add(formattedSlot)
                        }
                    }
                    response?.body()?.booked?.let { slots ->
                        slots.forEach { js ->
                            val formattedSlot = TimeSlot(
                                startTime = DateTimeUtils.jsFormatToTimestamp(js.start),
                                endTime = DateTimeUtils.jsFormatToTimestamp(js.end),
                                available = false
                            )
                            timeSlots.add(formattedSlot)
                        }
                    }
                    timeSlots.sortBy { slot -> slot.startTime }
                    it.resumeWith(Result.success(timeSlots))
                }

                override fun onFailure(call: Call<ProvidedSlots>?, t: Throwable?) {
                    it.resumeWith(Result.failure(Exception(t)))
                }
            })
        }
    }

    suspend fun queryTestData(): List<User> {
        return suspendCancellableCoroutine {
            service.getTestData(2).enqueue(object : Callback<TestData> {

                override fun onResponse(call: Call<TestData>?, response: Response<TestData>?) {
                    val users = ArrayList<User>()
                    response?.body()?.data?.let {
                        users.addAll(it)
                    }
                    it.resumeWith(Result.success(users))
                }

                override fun onFailure(call: Call<TestData>?, t: Throwable?) {
                    it.resumeWith(Result.failure(Exception(t)))
                }
            })
        }
    }

    companion object {
        private const val API_URL = "https://reqres.in/"
    }
}