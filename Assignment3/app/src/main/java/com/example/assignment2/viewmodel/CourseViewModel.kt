package com.example.assignment2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment2.data.repository.CourseRepository
import com.example.assignment2.model.Course
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CourseViewModel(private val repository: CourseRepository) : ViewModel() {

    // Courses loaded from database via repository - using stateIn for cleaner approach
    val courses: StateFlow<List<Course>> = repository.allCourses
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            emptyList()
        )

    // Selected course
    private val _selectedCourse = MutableStateFlow<Course?>(null)
    val selectedCourse: StateFlow<Course?> = _selectedCourse.asStateFlow()

    // Course being edited
    private val _editingCourse = MutableStateFlow<Course?>(null)
    val editingCourse: StateFlow<Course?> = _editingCourse.asStateFlow()

    // Add a new course
    fun addCourse(department: String, courseNumber: String, location: String) {
        viewModelScope.launch {
            repository.addCourse(department, courseNumber, location)
        }
    }

    // Delete a course
    fun deleteCourse(course: Course) {
        viewModelScope.launch {
            repository.deleteCourse(course)
            // Clear selection if the deleted course was selected
            if (_selectedCourse.value?.id == course.id) {
                _selectedCourse.value = null
            }
        }
    }

    // Select a course to view details
    fun selectCourse(course: Course?) {
        _selectedCourse.value = course
    }

    // Start editing a course
    fun startEditing(course: Course) {
        _editingCourse.value = course
    }

    // Cancel editing
    fun cancelEditing() {
        _editingCourse.value = null
    }

    // Update an existing course
    fun updateCourse(courseId: Int, department: String, courseNumber: String, location: String) {
        viewModelScope.launch {
            repository.updateCourse(courseId, department, courseNumber, location)
            _editingCourse.value = null
            // Update selected course if it was edited
            _selectedCourse.value = repository.getCourseById(courseId)
        }
    }
}