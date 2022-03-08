package com.example.scheduleappointment

import java.text.SimpleDateFormat
import java.util.*

class DateTimeUtils {
    companion object {
        const val minute = 60000L
        const val hour = minute * 60
        const val day = hour * 24
        const val week = day * 7

        fun getTodayTimestamp(): Long {
            val current = System.currentTimeMillis()
            return current - (current % day)
        }

        fun getSundayTimestamp(): Long {
            val today = getTodayTimestamp()
            // 1970-01-01 is Thursday
            return today - ((today - 3 * day) % week )
        }

        fun timestampToYearDate(timestamp: Long): String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(timestamp)

        fun timestampToDate(timestamp: Long): String = SimpleDateFormat("MM-dd", Locale.getDefault()).format(timestamp)
        
        fun timestampToDay(timestamp: Long): String = SimpleDateFormat("dd", Locale.getDefault()).format(timestamp)
        
        fun dateToTimestamp(dateFormat: String): Long {
            // 2020-07-17T10:30:00Z
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            return simpleDateFormat.parse(dateFormat)?.time ?: 0L
        }
        
        fun getCurrentHour(): Long {
            val current = System.currentTimeMillis()
            return current - (current % hour)
        }
        
        fun timestamp2test(timestamp: Long): String = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(timestamp)
        
        fun getTime(dateFormat: String): String {
            val originFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            return SimpleDateFormat("HH:mm", Locale.getDefault()).format(originFormat.parse(dateFormat) ?: Date())
        }
    }
}