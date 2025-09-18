package com.example.assignment2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment2.model.Course
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CourseViewModel : ViewModel() {
    // List of courses
    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> = _courses.asStateFlow()

    // Selected course
    private val _selectedCourse = MutableStateFlow<Course?>(null)
    val selectedCourse: StateFlow<Course?> = _selectedCourse.asStateFlow()

    // Course being edited
    private val _editingCourse = MutableStateFlow<Course?>(null)
    val editingCourse: StateFlow<Course?> = _editingCourse.asStateFlow()

    // Counter for generating unique IDs
    private var nextId = 1

    // Add a new course
    fun addCourse(department: String, courseNumber: String, location: String) {
        viewModelScope.launch {
            val newCourse = Course(
                id = nextId++,
                department = department.trim(),
                courseNumber = courseNumber.trim(),
                location = location.trim()
            )
            _courses.value = _courses.value + newCourse
        }
    }

    // Delete a course
    fun deleteCourse(course: Course) {
        viewModelScope.launch {
            _courses.value = _courses.value.filter { it.id != course.id }
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
            _courses.value = _courses.value.map { course ->
                if (course.id == courseId) {
                    course.copy(
                        department = department.trim(),
                        courseNumber = courseNumber.trim(),
                        location = location.trim()
                    )
                } else {
                    course
                }
            }
            _editingCourse.value = null
            // Update selected course if it was edited
            if (_selectedCourse.value?.id == courseId) {
                _selectedCourse.value = _courses.value.find { it.id == courseId }
            }
        }
    }
}