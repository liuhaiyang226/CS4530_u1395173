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
import com.example.assignment2.viewmodel.CourseViewModelFactory

class MainActivity : ComponentActivity() {
    private val courseViewModel: CourseViewModel by viewModels {
        CourseViewModelFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CourseManagerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CourseManagerApp(viewModel = courseViewModel)
                }
            }
        }
    }
}