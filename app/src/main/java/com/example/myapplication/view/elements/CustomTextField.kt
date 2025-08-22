package com.example.myapplication.view.elements

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myapplication.ui.theme.textFieldColors

@Composable
fun CustomTextField(
    value: String,
    onChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    supportingText: String = ""
) {
    OutlinedTextField(
        value = value,
        colors = MaterialTheme.textFieldColors,
        onValueChange = { onChange(it) },
        label = { Text(label) },
        modifier = modifier,
        supportingText = { Text(supportingText) }
    )
}

@Composable
fun CustomTextField(
    value: String,
    onChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = value,
        colors = MaterialTheme.textFieldColors,
        onValueChange = { onChange(it) },
        label = { Text(label) },
        modifier = modifier,
    )
}