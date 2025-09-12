package com.example.drawingdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.compose.AppTheme
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.ui.unit.dp
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.geometry.Size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.draw.clip

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                ThemeDemoScreen()
            }
        }
    }
}

@Composable
fun ThemeDemoScreen() {
    var clickCount by remember { mutableStateOf(0) }
    var selectedButton by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "🎨 Drawing Demo",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "with Custom Theme",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        // Button interaction feedback
        if (selectedButton.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            ) {
                Text(
                    text = "You clicked: $selectedButton (${clickCount}x)",
                    modifier = Modifier.padding(12.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }
        }

        // Primary Button
        Button(
            onClick = {
                clickCount++
                selectedButton = "Primary Button"
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Primary Color Button", fontWeight = FontWeight.Medium)
        }

        // Secondary Button
        FilledTonalButton(
            onClick = {
                clickCount++
                selectedButton = "Secondary Button"
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Secondary Color Button", fontWeight = FontWeight.Medium)
        }

        // Tertiary Outlined Button
        OutlinedButton(
            onClick = {
                clickCount++
                selectedButton = "Outlined Button"
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Outlined Button", fontWeight = FontWeight.Medium)
        }

        // Color Palette Display
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    "Theme Colors",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ColorSwatch(
                        color = MaterialTheme.colorScheme.primary,
                        label = "Primary"
                    )
                    ColorSwatch(
                        color = MaterialTheme.colorScheme.secondary,
                        label = "Secondary"
                    )
                    ColorSwatch(
                        color = MaterialTheme.colorScheme.tertiary,
                        label = "Tertiary"
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Drawing canvas with instructions
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    "Draw here with your finger!",
                    modifier = Modifier.padding(12.dp),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                DrawingCanvasPoints(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))
                )
            }
        }
    }
}

@Composable
fun ColorSwatch(color: Color, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Card(
            modifier = Modifier.size(60.dp),
            colors = CardDefaults.cardColors(containerColor = color),
            shape = RoundedCornerShape(8.dp)
        ) { }
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Composable
fun DrawingCanvasPoints(modifier: Modifier = Modifier) {
    var strokes by remember { mutableStateOf(listOf<List<Offset>>()) }
    var currentStroke by remember { mutableStateOf(listOf<Offset>()) }

    Canvas(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        currentStroke = listOf(offset)
                        strokes = strokes + listOf(currentStroke)
                    },
                    onDrag = { change, _ ->
                        change.consume()
                        currentStroke = currentStroke + change.position
                        strokes = strokes.dropLast(1) + listOf(currentStroke)
                    },
                    onDragEnd = {
                        currentStroke = emptyList()
                    }
                )
            }
    ) {
        strokes.forEach { stroke ->
            for (i in 0 until stroke.size - 1) {
                drawLine(
                    color = Color(0xFF6D5E0F),  // primary color
                    start = stroke[i],
                    end = stroke[i + 1],
                    strokeWidth = 6f
                )
            }
        }
    }
}

enum class BrushType {
    LINE, CIRCLE, RECTANGLE
}

@Composable
fun DrawingCanvas(brushType: BrushType = BrushType.CIRCLE) {
    var strokes by remember { mutableStateOf(listOf<List<Offset>>()) }
    var currentStroke by remember { mutableStateOf(listOf<Offset>()) }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        currentStroke = listOf(offset)
                        strokes = strokes + listOf(currentStroke)
                    },
                    onDrag = { change, _ ->
                        change.consume()
                        currentStroke = currentStroke + change.position
                        strokes = strokes.dropLast(1) + listOf(currentStroke)
                    },
                    onDragEnd = { currentStroke = emptyList() }
                )
            }
    ) {
        strokes.forEach { stroke ->
            when (brushType) {
                BrushType.LINE -> {
                    for (i in 0 until stroke.size - 1) {
                        drawLine(Color.Black, stroke[i], stroke[i + 1], strokeWidth = 4f)
                    }
                }
                BrushType.CIRCLE -> {
                    stroke.forEach { point ->
                        drawCircle(Color.Red, radius = 15f, center = point)
                    }
                }
                BrushType.RECTANGLE -> {
                    stroke.forEach { point ->
                        drawRect(
                            Color.Black,
                            topLeft = Offset(point.x - 8f, point.y - 8f),
                            size = Size(16f, 16f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DrawCirlce() {
    Column (Modifier.fillMaxWidth().padding(16.dp)) {
        Canvas(Modifier.size(100.dp)) {
            drawCircle(
                color = Color.Blue,
                radius = size.minDimension / 2
            )
        }
    }
}


