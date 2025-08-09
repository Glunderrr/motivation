package com.example.myapplication.view.screens.bottom.favorite

import com.example.myapplication.data.model.Phrase
import com.example.myapplication.view.screens.bottom.add.DrawerElement

sealed class FavUIAction {
    data class LongPressCard(
        val phrase: Phrase,
    ) : FavUIAction()

    data class TapCard(
        val phrase: Phrase,
    ) : FavUIAction()

    data class CopyPhrase(
        val phrase: Phrase,
    ) : FavUIAction()

    object ClearSelectedList : FavUIAction()
    object DeleteSelectedPhrases : FavUIAction()
    data class NavigateToAddScreen(
        val drawerElement: DrawerElement,
        val phrase: Phrase,
    ) : FavUIAction()

    data class SetNavigateFun(
        val navigateToAddScreen: (DrawerElement, Phrase) -> Unit,
    ) : FavUIAction()
}
