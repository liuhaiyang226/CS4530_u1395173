package com.example.assignment4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.assignment4.data.FunFact
import com.example.assignment4.ui.FactsViewModel

class MainActivity : ComponentActivity() {

    private val vm: FactsViewModel by viewModels {
        val app = application as FactsApp
        FactsViewModel.Factory(app.repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val state = vm.uiState.collectAsState()
                FactsScreen(
                    facts = state.value.facts,
                    onFetchClick = { vm.fetchNew() },
                    onClearClick = { vm.clearAll() }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FactsScreen(
    facts: List<FunFact>,
    onFetchClick: () -> Unit,
    onClearClick: () -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        TopAppBar(title = { Text("Useless Facts") })
        Row(
            Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = onFetchClick) {
                Text("Fetch new fact")
            }
            Spacer(Modifier.width(8.dp))
            Button(onClick = onClearClick)  {
                Text("Clear all")
            }
            Spacer(Modifier.width(12.dp))
            Text("Tap to add. List is stored locally (Room).")
        }
        Divider()
        if (facts.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No facts yet. Press the button!")
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(facts) { fact ->
                    FactRow(fact)
                }
            }
        }
    }
}

@Composable
private fun FactRow(fact: FunFact) {
    ElevatedCard(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(12.dp)) {
            Text(
                text = fact.text,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = "Saved: ${java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(fact.savedAtMillis)}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}