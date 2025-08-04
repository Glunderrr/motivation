package com.example.myapplication.view.screens.bottom.favorite

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.myapplication.R
import com.example.myapplication.data.model.Phrase
import com.example.myapplication.ui.theme.Paddings
import com.example.myapplication.view.elements.CustomTopBar
import com.example.myapplication.view.elements.EmptyListTitle
import com.example.myapplication.view.elements.PhraseCard
import com.example.myapplication.view.screens.bottom.add.DrawerElement

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Favorites(
    modifier: Modifier = Modifier,
    uiState: FavUIState,
    onAction: (FavUIAction) -> Unit,
) {
    if (uiState.favList.isEmpty()) {
        EmptyListTitle(
            upText = stringResource(R.string.fav_empty),
            downText = stringResource(R.string.pass_to_add_screen),
        ) {
            onAction(
                FavUIAction.NavigateToAddScreen(phrase = null)
            )
        }
    } else {
        Scaffold(
            topBar = {
                CustomTopBar(
                    showTopAppBar = uiState.selectedList.isNotEmpty(),
                    onClear = { onAction(FavUIAction.ClearSelectedList) },
                    onDelete = { onAction(FavUIAction.DeleteSelectedPhrases) },
                )
            }) { paddingValues ->
            LazyColumn(
                modifier = modifier.padding(
                    if (uiState.selectedList.isNotEmpty()) paddingValues
                    else PaddingValues()
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(Paddings.Large.dp),
                contentPadding = PaddingValues(vertical = Paddings.Large.dp),

                ) {
                items(uiState.favList) { phrase ->
                    PhraseCard(
                        modifier = Modifier.padding(horizontal = Paddings.Large.dp),
                        phrase = phrase,
                        onLongPress = {
                            onAction(FavUIAction.LongPressCard(phrase))
                        },
                        onTap = {
                            onAction(FavUIAction.TapCard(phrase))
                        },
                        onCopyClick = {
                            onAction(FavUIAction.CopyPhrase(phrase))
                        },
                        onEdit = {
                            onAction(FavUIAction.NavigateToAddScreen(phrase = phrase))
                        },
                        listIsEmpty = uiState.selectedList.isEmpty(),
                        selected = uiState.selectedList.map { it.id }.contains(phrase.id)
                    )
                }
            }
        }
    }
}