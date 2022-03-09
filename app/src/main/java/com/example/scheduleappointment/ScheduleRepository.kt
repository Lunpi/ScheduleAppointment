package com.example.scheduleappointment

import com.example.scheduleappointment.data.*
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class ScheduleRepository {

    private val httpClient = OkHttpClient.Builder().apply {
        addInterceptor { chain ->
            val request = chain.request().newBuilder().addHeader(HTTP_HEADER_KEY, HTTP_HEADER_VALUE).build()
            chain.proceed(request)
        }
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient.build())
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

    companion object {
        // TODO: Replace your API URL here
        private const val BASE_URL = "https://reqres.in/"
        // TODO: Customize headers if needed
        private const val HTTP_HEADER_KEY = "X-Auth-Token"
        private const val HTTP_HEADER_VALUE = "5b294e9193d240e39eefc5e6e551ce83"
    }
}