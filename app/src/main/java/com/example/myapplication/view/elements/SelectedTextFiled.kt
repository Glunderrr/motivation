package com.example.myapplication.view.elements

import android.R.string
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.Paddings
import com.example.myapplication.ui.theme.textFieldColors

@Composable
fun SelectTextField(
    modifier: Modifier = Modifier,
    value: String,
    onAgeSelected: (String) -> Unit,
    label: String,
    dialogLabel: String,
    list: List<String>
) {
    var showDialog by remember { mutableStateOf(false) }
    var flowText by remember { mutableStateOf(value) }

    OutlinedTextField(
        value = flowText,
        onValueChange = {},
        enabled = false,
        colors = MaterialTheme.textFieldColors,
        modifier = modifier
            .fillMaxWidth(0.9f)
            .pointerInput(Unit) { detectTapGestures { showDialog = true } },
        label = { Text(label) },
        trailingIcon = {
            Icon(Icons.Default.KeyboardArrowDown, contentDescription = null)
        }
    )

    if (showDialog) {
        AgePickerDialog(
            list = list,
            label = dialogLabel,
            onDismiss = { showDialog = false },
            onAgeSelected = { selected ->
                flowText = selected
                onAgeSelected(selected)
                showDialog = false
            }
        )
    }
}

@Composable
private fun AgePickerDialog(
    label: String,
    list: List<String>,
    onDismiss: () -> Unit,
    onAgeSelected: (String) -> Unit
) {
    var selectedAge by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = { onAgeSelected(selectedAge); onDismiss() },
                enabled = selectedAge.isNotEmpty()
            ) {
                Text(stringResource(string.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(id = string.cancel))
            }
        },
        title = { Text(label) },
        text = {
            LazyColumn(
                modifier = Modifier
                    .heightIn(max = 300.dp)
                    .background(colorScheme.background)
            ) {
                items(list.size) { index ->
                    val value = list[index]
                    Text(
                        text = value,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                if (value == selectedAge) colorScheme.primary.copy(
                                    alpha = 0.2f
                                )
                                else colorScheme.background
                            )
                            .padding(vertical = Paddings.ExtraSmall.dp)
                            .pointerInput(Unit) {
                                detectTapGestures {
                                    selectedAge = value
                                }
                            },
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    )
}