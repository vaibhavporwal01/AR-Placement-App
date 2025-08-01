package com.example.arplacementapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.arplacementapp.data.Drill
import com.example.arplacementapp.data.mockDrills

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrillSelectionScreen(
    drills: List<Drill>,
    onDrillSelected: (Drill) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedDrill by remember { mutableStateOf<Drill?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Select Your Drill", fontWeight = FontWeight.Bold) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Choose a drill to view its details.",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Dropdown menu for drill selection
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                TextField(
                    value = selectedDrill?.name ?: "Select a Drill",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Drill") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    drills.forEach { drill ->
                        DropdownMenuItem(
                            text = { Text(drill.name) },
                            onClick = {
                                selectedDrill = drill
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    selectedDrill?.let {
                        onDrillSelected(it)
                    } ?: run {
                        // We could optionally show a toast or message if no drill is selected
                        // Toast.makeText(context, "Please select a drill first", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(56.dp),
                enabled = selectedDrill != null
            ) {
                Text("View Drill Details", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDrillSelectionScreenWithDropdown() {
    MaterialTheme {
        DrillSelectionScreen(drills = mockDrills, onDrillSelected = {})
    }
}