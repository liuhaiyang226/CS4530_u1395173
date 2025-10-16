package com.example.assignment2.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.assignment2.model.Course

@Entity(tableName = "courses")
data class CourseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val department: String,
    val courseNumber: String,
    val location: String
) {
    // Convert database entity to domain model
    fun toCourse(): Course {
        return Course(
            id = id,
            department = department,
            courseNumber = courseNumber,
            location = location
        )
    }

    companion object {
        // Convert domain model to database entity
        fun fromCourse(course: Course): CourseEntity {
            return CourseEntity(
                id = course.id,
                department = course.department,
                courseNumber = course.courseNumber,
                location = course.location
            )
        }
    }
}