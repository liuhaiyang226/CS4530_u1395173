package com.example.assignment2.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.assignment2.ui.screens.*
import com.example.assignment2.viewmodel.CourseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseManagerApp(viewModel: CourseViewModel) {
    val navController = rememberNavController()
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Course Manager") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            // Show FAB only on the main list screen
            val currentRoute by navController.currentBackStackEntryFlow.collectAsState(initial = null)
            if (currentRoute?.destination?.route == "course_list" || currentRoute == null) {
                FloatingActionButton(
                    onClick = { showAddDialog = true },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Course")
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            NavigationHost(navController, viewModel)

            // Add Course Dialog
            if (showAddDialog) {
                AddCourseDialog(
                    onDismiss = { showAddDialog = false },
                    onConfirm = { dept, number, loc ->
                        viewModel.addCourse(dept, number, loc)
                        showAddDialog = false
                    }
                )
            }
        }
    }
}

@Composable
fun NavigationHost(
    navController: NavHostController,
    viewModel: CourseViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "course_list"
    ) {
        composable("course_list") {
            CourseListScreen(
                viewModel = viewModel,
                onCourseClick = { course ->
                    viewModel.selectCourse(course)
                    navController.navigate("course_details")
                }
            )
        }

        composable("course_details") {
            CourseDetailsScreen(
                viewModel = viewModel,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}