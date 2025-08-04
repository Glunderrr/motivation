package com.example.myapplication.view.screens.bottom.add

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.Phrase
import com.example.myapplication.usecases.phrase.GeneratePhraseByTheme
import com.example.myapplication.usecases.phrase.SavePhrase
import com.example.myapplication.usecases.theme.GetTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class AddViewModel @Inject constructor(
    private val themeUseCase: GetTheme,
    private val savePhraseUseCase: SavePhrase,
    private val generatePhraseByThemeUseCase: GeneratePhraseByTheme
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddUIState())
    val uiState: StateFlow<AddUIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            themeUseCase.invoke().collect { themes ->
                _uiState.update { state ->
                    state.copy(
                        themes = themes
                    )
                }
            }
        }
    }

    fun onAction(action: AddUIAction) {
        when (action) {
            is AddUIAction.GeneratePhrase -> {
                viewModelScope.launch {
                    val newPhrase =
                        generatePhraseByThemeUseCase.invoke(_uiState.value.selectedTheme)
                    _uiState.update { it.copy(newPhrase = newPhrase) }
                }
            }

            is AddUIAction.SavePhrase -> {
                viewModelScope.launch {
                    savePhraseUseCase.invoke(_uiState.value.newPhrase!!)
                }
            }

            is AddUIAction.OpenThemeDialog -> {
                _uiState.update { it.copy(openThemeDialog = true) }
            }

            is AddUIAction.CloseThemeDialog -> {
                _uiState.update { it.copy(openThemeDialog = false) }
            }


            is AddUIAction.OpenPhraseDialog -> {
                _uiState.update { it.copy(openPhraseDialog = true) }
            }

            is AddUIAction.ClosePhraseDialog -> {
                _uiState.update { it.copy(openPhraseDialog = false) }
            }

            is AddUIAction.SelectTheme -> {
                _uiState.update { it.copy(selectedTheme = action.theme) }
            }

            is AddUIAction.IsLoading -> {
                _uiState.update { it.copy(isLoading = action.value) }
            }

            is AddUIAction.ChangeLikedStatusOfNewPhrase -> {
                _uiState.update { it.copy(newPhrase = it.newPhrase!!.copy(isLiked = !it.newPhrase.isLiked)) }
            }


            is AddUIAction.ShowDrawerSheet -> {
                _uiState.update { it.copy(showDrawerSheet = action.value) }
            }

            is AddUIAction.ChangeSelectedDrawerElement -> {
                _uiState.update {
                    it.copy(
                        selectedDrawerElement = action.element,
                        selectedTheme = "",
                        newPhrase = null,
                    )
                }
/*                Log.d(
                    "AddViewModel",
                    "ChangeSelectedDrawerElement: ${_uiState.value.selectedDrawerElement.route}"
                )*/
            }

            is AddUIAction.TypingOwnPhrase -> {
                _uiState.update { it.copy(ownPhraseText = action.value) }
            }

            AddUIAction.SaveOwnPhrase -> {
                viewModelScope.launch {
                    savePhraseUseCase.invoke(
                        Phrase(
                            phrase = _uiState.value.ownPhraseText,
                            theme = _uiState.value.selectedTheme,
                            isOwn = true
                        )
                    )
                    _uiState.update {
                        it.copy(
                            ownPhraseText = "",
                            selectedTheme = ""
                        )
                    }
                }
            }

            is AddUIAction.OnAboutProgramClick -> TODO()
        }
    }
}