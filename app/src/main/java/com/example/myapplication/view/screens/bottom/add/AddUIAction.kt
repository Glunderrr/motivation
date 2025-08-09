package com.example.myapplication.view.screens.bottom.add

import com.example.myapplication.data.model.Phrase

sealed class AddUIAction {
    data object SavePhrase : AddUIAction()

    data class ChangeLikedStatusOfNewPhrase(
        val phrase: Phrase
    ) : AddUIAction()

    data object OpenThemeDialog : AddUIAction()
    data object CloseThemeDialog : AddUIAction()

    data object OpenPhraseDialog : AddUIAction()
    data object ClosePhraseDialog : AddUIAction()

    data object GeneratePhrase : AddUIAction()

    data class SelectTheme(
        val theme: String
    ) : AddUIAction()

    data class IsLoading(
        val value: Boolean
    ) : AddUIAction()

    data class ShowDrawerSheet(
        val value: Boolean
    ) : AddUIAction()

    object OnAboutProgramClick : AddUIAction()

    data class ChangeSelectedDrawerElement(
        val element: DrawerElement
    ) : AddUIAction()

    data class TypingPhraseText(
        val value: String
    ) : AddUIAction()

    data class SetPhraseToEdit(
        val phrase: Phrase
    ) : AddUIAction()
}