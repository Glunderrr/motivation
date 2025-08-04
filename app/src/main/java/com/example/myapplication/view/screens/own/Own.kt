package com.example.myapplication.view.screens.own

import androidx.compose.foundation.layout.Arrangement
import com.example.myapplication.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.Paddings
import com.example.myapplication.view.elements.ChooseThemeButton
import com.example.myapplication.view.screens.bottom.add.AddUIAction
import com.example.myapplication.view.screens.bottom.add.AddUIState
import java.nio.file.WatchEvent


@Composable
fun Own(
    modifier: Modifier = Modifier,
    uiState: AddUIState,
    onAction: (AddUIAction) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Paddings.Medium.dp),
    ) {
        ChooseThemeButton(
            modifier = Modifier.fillMaxWidth(0.9f),
            onClick = {
                onAction(AddUIAction.OpenThemeDialog())
            },
            text = uiState.selectedTheme,
        )
        OutlinedTextField(
            modifier = Modifier
                .weight(9f)
                .fillMaxWidth(0.9f),
            value = uiState.ownPhraseText,
            onValueChange = {
                onAction(AddUIAction.TypingOwnPhrase(it))
            },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.own_phrase_placeholder),
                    style = typography.bodyMedium
                )
            }
        )
        Button(
            modifier = Modifier.fillMaxWidth(0.9f),
            enabled = uiState.ownPhraseText.isNotEmpty() && uiState.selectedTheme.isNotEmpty(),
            onClick = {
                onAction(AddUIAction.SaveOwnPhrase)
            }
        ) {
            Text(
                text = stringResource(id = R.string.save),
                style = typography.bodyMedium
            )
        }
    }
}