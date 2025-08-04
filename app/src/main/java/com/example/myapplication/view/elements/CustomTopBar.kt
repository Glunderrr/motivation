package com.example.myapplication.view.elements

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.Paddings

@Composable
fun CustomTopBar(
    showTopAppBar: Boolean,
    onClear: () -> Unit,
    onDelete: () -> Unit,
) {
    AnimatedVisibility(
        showTopAppBar,
        enter = slideInVertically { -it } + expandVertically(expandFrom = Alignment.Top) + fadeIn(
            initialAlpha = 0.3f
        ),
        exit = slideOutVertically() + shrinkVertically()
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.primary),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = onClear,
                modifier = Modifier
                    .padding(Paddings.Medium.dp)
            ) {
                Icon(
                    modifier = Modifier.size(50.dp),
                    imageVector = Icons.Default.Clear,
                    tint = MaterialTheme.colorScheme.background,
                    contentDescription = "Close button"
                )
            }

            IconButton(
                onClick = onDelete,
                modifier = Modifier.padding(Paddings.Medium.dp)
            ) {
                Icon(
                    modifier = Modifier.size(50.dp),
                    imageVector = Icons.Default.Delete,
                    tint = MaterialTheme.colorScheme.errorContainer,
                    contentDescription = "Delete button"
                )
            }
        }
    }
}
