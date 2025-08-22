package com.example.myapplication.view.screens.bottom.profile

import com.example.myapplication.data.model.Phrase
import com.example.myapplication.data.model.Theme
import com.example.myapplication.view.screens.bottom.add.DrawerElement
import com.example.myapplication.view.screens.bottom.favorite.FavUIAction

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


    data class CopyToClipBoard(
        val phrase: Phrase,
    ) : ProfUIAction()

    data class NavigateToAddScreen(
        val drawerElement: DrawerElement,
        val phrase: Phrase = Phrase(),
    ) : ProfUIAction()

    data class SetNavigateFun(
        val navigateToAddScreen: (DrawerElement, Phrase) -> Unit,
    ) : ProfUIAction()

    data class SetPersonalData(
        val key: String,
        val value: Any?
    ) : ProfUIAction()

    object SavePersonalData : ProfUIAction()
}