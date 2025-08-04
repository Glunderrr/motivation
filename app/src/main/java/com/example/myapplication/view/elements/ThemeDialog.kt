package com.example.myapplication.view.elements

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.myapplication.R
import com.example.myapplication.data.model.Theme

@Composable
fun ThemeDialog(
    modifier: Modifier = Modifier,
    showState: Boolean,
    onDismissRequest: () -> Unit,
    themeList: List<Theme>,
    onClick: (Theme) -> Unit
) {
    if (showState) {
        AlertDialog(
            modifier = modifier,
            onDismissRequest = onDismissRequest ,
            title = { Text(text = stringResource(R.string.select_theme)) },
            text = {
                LazyColumn {
                    items(themeList) { item ->
                        DropdownMenuItem(
                            text = { Text(item.themeName) },
                            onClick = {
                                onClick(item)
                            }
                        )
                    }
                }
            },
            confirmButton = {},
            dismissButton = {}
        )
    }
}

