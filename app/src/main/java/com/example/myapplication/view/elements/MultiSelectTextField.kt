package com.example.myapplication.view.elements

import android.R.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.Paddings
import com.example.myapplication.ui.theme.textFieldColors

@Composable
fun MultiSelectTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueSelected: (String) -> Unit,
    label: String,
    dialogLabel: String,
    options: List<String>
) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedOptions by remember { mutableStateOf(value.split(", ").filter { it.isNotEmpty() }) }

    OutlinedTextField(
        value = value,
        onValueChange = { /* Read-only field */ },
        label = { Text(label) },
        modifier = modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures { showDialog = true }
            },
        enabled = false,
        colors = MaterialTheme.textFieldColors
    )

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(dialogLabel) },
            text = {
                LazyColumn {
                    item {
                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(Paddings.Small.dp),
                            verticalArrangement = Arrangement.spacedBy(Paddings.Small.dp),
                        ) {
                            options.forEach { option ->
                                val isSelected = selectedOptions.contains(option)
                                FilterChip(
                                    modifier = Modifier,
                                    selected = isSelected,
                                    onClick = {
                                        selectedOptions = if (isSelected) {
                                            selectedOptions - option
                                        } else {
                                            selectedOptions + option
                                        }
                                    },
                                    label = { Text(option) },
                                    leadingIcon = if (isSelected) {
                                        {
                                            Icon(
                                                imageVector = Icons.Filled.Check,
                                                contentDescription = "Selected",
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                    } else null,
                                    shape = RoundedCornerShape(corner = CornerSize(Paddings.Small.dp)),
                                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val newValue = selectedOptions.joinToString(", ")
                        onValueSelected(newValue)
                        showDialog = false
                    },
                    enabled = selectedOptions.isNotEmpty()
                ) {
                    Text(stringResource(string.ok))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(stringResource(id = string.cancel))
                }
            }
        )
    }
}