package com.example.myapplication.view.screens.bottom.profile

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.myapplication.data.model.Personal
import com.example.myapplication.data.model.Phrase
import com.example.myapplication.data.model.Theme
import com.example.myapplication.view.screens.bottom.add.DrawerElement

data class ProfUIState(
    val personalData: Personal? = null,
    val themes: List<Theme> = emptyList(),
    val isAllPhrase: Boolean = true,
    val selectedThemes: List<Theme> = emptyList(),
    val allPhraseList: List<Phrase> = emptyList(),
    val ownList: List<Phrase> = emptyList(),
    val selectedPhrases: List<Phrase> = emptyList(),
    val navigateToAddScreen: (DrawerElement,Phrase) -> Unit = { _, _ -> },
)