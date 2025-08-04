package com.example.myapplication.view.screens.bottom.profile

import com.example.myapplication.data.model.Phrase
import com.example.myapplication.data.model.Theme

sealed class ProfUIAction {
    data class ChangeThemeStatusInSelectedList(
        val theme: Theme
    ) : ProfUIAction()

    data class ChangeIsAllPhrase(
        val isAllPhrase: Boolean
    ) : ProfUIAction()

    object ClearPhraseSelectedList : ProfUIAction()
    object DeleteSelectedPhrase : ProfUIAction()

    data class LongPressCard(
        val phrase: Phrase,
    ) : ProfUIAction()

    data class TapCard(
        val phrase: Phrase,
    ) : ProfUIAction()

    data class DoubleTapCard(
        val phrase: Phrase
    ) : ProfUIAction()

    object NavigateToAddScreen : ProfUIAction()

    data class CopyToClipBoard(
        val phrase: Phrase,
    ) : ProfUIAction()
}