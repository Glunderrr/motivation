package com.example.myapplication.view.screens.bottom.add

import com.example.myapplication.data.model.Phrase
import com.example.myapplication.data.model.Theme

data class AddUIState(
    val themes: List<Theme> = emptyList(),
    val openThemeDialog: Boolean = false,
    val openPhraseDialog: Boolean = false,
    val selectedTheme: String = "",
    val isLoading: Boolean = false,
    val newPhrase: Phrase? = null,
    val showDrawerSheet: Boolean = false,
    val selectedDrawerElement: DrawerElement = DrawerElement.Generate,
    val ownPhraseText: String = ""
)
