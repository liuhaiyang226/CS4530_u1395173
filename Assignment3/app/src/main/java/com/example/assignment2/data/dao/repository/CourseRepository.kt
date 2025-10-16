package com.example.assignment2.data.repository

import com.example.assignment2.data.dao.CourseDao
import com.example.assignment2.data.entity.CourseEntity
import com.example.assignment2.model.Course
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CourseRepository(private val courseDao: CourseDao) {

    // Get all courses as a Flow (automatically updates when database changes)
    val allCourses: Flow<List<Course>> = courseDao.getAllCourses()
        .map { courseEntities ->
            courseEntities.map { it.toCourse() }
        }

    // Add a new course
    suspend fun addCourse(department: String, courseNumber: String, location: String) {
        val courseEntity = CourseEntity(
            department = department,
            courseNumber = courseNumber,
            location = location
        )
        courseDao.insert(courseEntity)
    }

    // Delete a course
    suspend fun deleteCourse(course: Course) {
        courseDao.delete(CourseEntity.fromCourse(course))
    }

    // Update a course
    suspend fun updateCourse(courseId: Int, department: String, courseNumber: String, location: String) {
        val updatedCourse = CourseEntity(
            id = courseId,
            department = department,
            courseNumber = courseNumber,
            location = location
        )
        courseDao.update(updatedCourse)
    }

    // Get a course by ID
    suspend fun getCourseById(courseId: Int): Course? {
        return courseDao.getCourseById(courseId)?.toCourse()
    }

    companion object {
        @Volatile
        private var INSTANCE: CourseRepository? = null

        // Singleton pattern to ensure only one instance exists
        fun getInstance(courseDao: CourseDao): CourseRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = CourseRepository(courseDao)
                INSTANCE = instance
                instance
            }
        }
    }
}