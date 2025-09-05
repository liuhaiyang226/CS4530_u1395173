package com.example.composedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.composedemo.ui.theme.ComposeDEMOTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Alignment
import com.example.composedemo.ui.theme.LightRed

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeDEMOTheme {
                TextConcatenator()
            }
        }
    }
}

@Composable
fun TextConcatenator() {
    var text1 by remember { mutableStateOf("") }
    var text2 by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // First TextField
        OutlinedTextField(
            value = text1,
            onValueChange = { text1 = it },
            label = { Text("First Text") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Second TextField
        OutlinedTextField(
            value = text2,
            onValueChange = { text2 = it },
            label = { Text("Second Text") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Button to concatenate
        Button(
            onClick = {
                result = text1 + text2
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Concatenate")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display the result
        if (result.isNotEmpty()) {
            Text(
                text = "Result: $result",
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun Counter() {
    Column(Modifier.fillMaxWidth().padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        var text by remember { mutableStateOf("") }

        Row {
            OutlinedTextField(
                value = text,
                onValueChange = { newText -> text = newText },
                label = { Text("Name") }
            )
        }

        Row {
            Button(onClick = { text = "" }) {
                Text("Clear the text field")
            }
        }
    }
}

@Composable
fun JPCPractice(name: String) {
    Column(modifier = Modifier.fillMaxSize().padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Row(Modifier.background(Color.LightGray)) {
            Text(
                text = "$name!",
                Modifier.padding(8.dp),
                color = Color.Black,
                fontSize = 16.sp
            )
            Text("CS 3505",
                Modifier.padding(8.dp),
                color = Color.Black,
                fontSize = 16.sp)
        }

        Row(Modifier.background(LightRed).padding(15.dp),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text("CS Major",
                Modifier.padding(8.dp),
                color = Color.Black,
                fontSize = 16.sp)
            Text("BS Major",
                Modifier.padding(8.dp),
                color = Color.Black,
                fontSize = 16.sp)
        }
    }
}