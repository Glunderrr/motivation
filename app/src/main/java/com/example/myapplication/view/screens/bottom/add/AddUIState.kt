package com.example.myapplication.view.screens.bottom.add

import com.example.myapplication.data.model.Phrase
import com.example.myapplication.data.model.Theme

data class AddUIState(
    val themes: List<Theme> = emptyList(),
    val openThemeDialog: Boolean = false,
    val openPhraseDialog: Boolean = false,
    val isLoading: Boolean = false,
    val phrase: Phrase = Phrase(),
    val showDrawerSheet: Boolean = false,
    val selectedDrawerElement: DrawerElement = DrawerElement.Generate,
    val aboutProgramClickFun: () -> Unit = {}
)
