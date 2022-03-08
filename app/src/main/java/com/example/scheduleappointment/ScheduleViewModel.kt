package com.example.scheduleappointment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scheduleappointment.data.TimeSlot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class ScheduleViewModel : ViewModel() {

    private val repository = ScheduleRepository()

    val timeSlots = MutableLiveData<List<TimeSlot>>()
    val processing = MutableLiveData(false)
    val errorMessage = MutableLiveData("")

    fun updateTimeSlots(date: Long) {
        processing.value = true
        viewModelScope.launch(Dispatchers.Default) {
            try {
                val result = repository.query(date)
                val testData = repository.queryTestData()
                processing.postValue(false)
                timeSlots.postValue(result)
            } catch (e: Exception) {
                processing.postValue(false)
                errorMessage.postValue(e.message ?: ERROR_UNKNOWN)
            }
        }
    }


    companion object {
        const val ERROR_UNKNOWN = "error_unknown"
    }
}