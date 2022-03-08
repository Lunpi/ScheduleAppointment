package com.example.scheduleappointment

import java.text.SimpleDateFormat
import java.util.*

class DateTimeUtils {
    companion object {
        const val minute = 60000L
        const val hour = minute * 60
        const val day = hour * 24
        const val week = day * 7

        private const val yMdFormat = "yyyy-MM-dd"
        private const val mdFormat = "MM-dd"
        private const val dFormat = "dd"
        private const val hmsFormat = "HH:mm:ss"
        private const val hmFormat = "HH:mm"

        fun getTodayTimestamp(): Long {
            val current = System.currentTimeMillis()
            return current - (current % day) - TimeZone.getDefault().rawOffset
        }

        fun getSundayTimestamp(): Long {
            val today = getTodayTimestamp()
            // 1970-01-01 is Thursday
            return today - ((today - 3 * day) % week ) - TimeZone.getDefault().rawOffset
        }

        fun timestampToYearDate(timestamp: Long): String = SimpleDateFormat(yMdFormat, Locale.getDefault()).format(timestamp)

        fun timestampToDate(timestamp: Long): String = SimpleDateFormat(mdFormat, Locale.getDefault()).format(timestamp)

        fun timestampToDay(timestamp: Long): String = SimpleDateFormat(dFormat, Locale.getDefault()).format(timestamp)

        fun timestampToHourMinute(timestamp: Long): String = SimpleDateFormat(hmFormat, Locale.getDefault()).format(timestamp)

        fun jsFormatToTimestamp(date: String): Long {
            // 2020-07-17T10:30:00Z -> 2020-07-17 10:30:00
            val dateFormat = date.replace('T', ' ', false).dropLast(1)
            val simpleDateFormat = SimpleDateFormat("$yMdFormat $hmsFormat", Locale.getDefault())
            return simpleDateFormat.parse(dateFormat)?.time ?: 0L
        }
    }
}