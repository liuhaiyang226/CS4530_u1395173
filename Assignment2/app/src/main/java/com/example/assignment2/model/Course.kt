package com.example.assignment2.model

data class Course(
    val id: Int = 0,
    val department: String,
    val courseNumber: String,
    val location: String
) {
    // Display name for the list
    val displayName: String
        get() = "$department $courseNumber"
}