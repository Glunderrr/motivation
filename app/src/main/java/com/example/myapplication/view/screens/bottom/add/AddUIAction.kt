package com.example.myapplication.view.screens.bottom.add

import com.example.myapplication.data.model.Phrase

sealed class AddUIAction {
    data class SavePhrase(
        val phrase: Phrase
    ) : AddUIAction()

    data class ChangeLikedStatusOfNewPhrase(
        val phrase: Phrase
    ) : AddUIAction()

    class OpenThemeDialog() : AddUIAction()
    class CloseThemeDialog() : AddUIAction()

    class OpenPhraseDialog() : AddUIAction()
    class ClosePhraseDialog() : AddUIAction()

    data class GeneratePhrase(
        val theme: String
    ) : AddUIAction()

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

    data class TypingOwnPhrase(
        val value: String
    ) : AddUIAction()

    object SaveOwnPhrase : AddUIAction()
}