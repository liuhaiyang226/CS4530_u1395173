package com.example.assignment2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.assignment2.ui.CourseManagerApp
import com.example.assignment2.ui.theme.CourseManagerTheme
import com.example.assignment2.viewmodel.CourseViewModel

class MainActivity : ComponentActivity() {
    // Create ViewModel instance
    private val courseViewModel: CourseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CourseManagerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Pass ViewModel to the main composable
                    CourseManagerApp(viewModel = courseViewModel)
                }
            }
        }
    }
}