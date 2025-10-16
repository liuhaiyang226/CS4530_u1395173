package com.example.assignment2.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.assignment2.data.CourseDatabase
import com.example.assignment2.data.repository.CourseRepository

class CourseViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CourseViewModel::class.java)) {
            // Get singleton instance of repository
            val database = CourseDatabase.getDatabase(application)
            val courseDao = database.courseDao()
            val repository = CourseRepository.getInstance(courseDao)

            @Suppress("UNCHECKED_CAST")
            return CourseViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}