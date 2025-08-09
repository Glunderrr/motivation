package com.example.myapplication.view.screens.bottom.favorite

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.myapplication.data.model.Phrase
import com.example.myapplication.data.model.Theme
import com.example.myapplication.view.screens.bottom.add.DrawerElement

data class FavUIState(
    val navigateToAddScreen: (DrawerElement,Phrase) -> Unit = { _, _ -> },
    val selectedList: List<Phrase> = emptyList(),
    val favList: List<Phrase> = emptyList(),
)
