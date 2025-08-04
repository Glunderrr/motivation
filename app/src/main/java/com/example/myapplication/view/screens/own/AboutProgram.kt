package com.example.myapplication.view.screens.own

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AboutProgram(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
    ) {
        Text(
            text = "This is a sample program to demonstrate the About section.",
        )
    }
}