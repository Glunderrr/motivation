package com.example.myapplication.view.screens.own

import androidx.compose.foundation.layout.Arrangement
import com.example.myapplication.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.myapplication.ui.theme.Paddings
import com.example.myapplication.view.elements.ChooseThemeButton
import com.example.myapplication.view.screens.bottom.add.AddUIAction
import com.example.myapplication.view.screens.bottom.add.AddUIState


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
                onAction(AddUIAction.OpenThemeDialog)
            },
            text = uiState.phrase.theme,
        )
        OutlinedTextField(
            modifier = Modifier
                .weight(9f)
                .fillMaxWidth(0.9f),
            value = uiState.phrase.phrase,
            onValueChange = {
                onAction(AddUIAction.TypingPhraseText(it))
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
            enabled = uiState.phrase.phrase.isNotEmpty() && uiState.phrase.theme.isNotEmpty(),
            onClick = {
                onAction(AddUIAction.OpenPhraseDialog)
            }
        ) {
            Text(
                text = stringResource(id = R.string.add),
                style = typography.bodyMedium
            )
        }
    }
}