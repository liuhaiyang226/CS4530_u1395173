package com.example.assignment2.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.assignment2.viewmodel.CourseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseDetailsScreen(
    viewModel: CourseViewModel,
    onBack: () -> Unit
) {
    val selectedCourse by viewModel.selectedCourse.collectAsState()
    val editingCourse by viewModel.editingCourse.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Back button
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = "Course Details",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.weight(1f)
            )
            // Edit button
            selectedCourse?.let { course ->
                IconButton(onClick = { viewModel.startEditing(course) }) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit Course")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        selectedCourse?.let { course ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    // Course Name
                    Text(
                        text = course.displayName,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Department
                    DetailRow(
                        label = "Department",
                        value = course.department
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Course Number
                    DetailRow(
                        label = "Course Number",
                        value = course.courseNumber
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Location
                    DetailRow(
                        label = "Location",
                        value = course.location
                    )
                }
            }
        } ?: run {
            // Fallback if no course is selected
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No course selected")
            }
        }
    }

    // Edit dialog
    editingCourse?.let { course ->
        EditCourseDialog(
            course = course,
            onDismiss = { viewModel.cancelEditing() },
            onConfirm = { dept, number, loc ->
                viewModel.updateCourse(course.id, dept, number, loc)
            }
        )
    }
}

@Composable
fun DetailRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.width(120.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}