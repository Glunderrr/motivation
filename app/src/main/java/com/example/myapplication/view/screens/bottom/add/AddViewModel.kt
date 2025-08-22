package com.example.myapplication.view.screens.bottom.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.global_states.UserParametersState
import com.example.myapplication.data.model.Phrase
import com.example.myapplication.usecases.phrase.GeneratePhrase
import com.example.myapplication.usecases.phrase.UpsertPhrase
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
    private val upsertPhraseUseCase: UpsertPhrase,
    private val generatePhraseUseCase: GeneratePhrase,
    private val userParametersState: UserParametersState
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
                        generatePhraseUseCase.invoke(
                            _uiState.value.phrase.theme,
                            userParametersState
                        )
                    _uiState.update { it.copy(phrase = newPhrase) }
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
                _uiState.update {
                    it.copy(
                        phrase = it.phrase.copy(
                            theme = action.theme
                        )
                    )
                }
            }

            is AddUIAction.IsLoading -> {
                _uiState.update { it.copy(isLoading = action.value) }
            }

            is AddUIAction.ChangeLikedStatusOfNewPhrase -> {
                _uiState.update { it.copy(phrase = it.phrase.copy(isLiked = !it.phrase.isLiked)) }
            }


            is AddUIAction.ShowDrawerSheet -> {
                _uiState.update { it.copy(showDrawerSheet = action.value) }
            }

            is AddUIAction.ChangeSelectedDrawerElement -> {
                _uiState.update {
                    it.copy(
                        selectedDrawerElement = action.element,
                        phrase = Phrase()
                    )
                }
            }

            is AddUIAction.TypingPhraseText -> {
                _uiState.update {
                    it.copy(
                        phrase = it.phrase.copy(
                            phrase = action.value
                        )
                    )
                }
            }

            AddUIAction.SavePhrase -> {
                viewModelScope.launch {
                    upsertPhraseUseCase.invoke(
                        _uiState.value.phrase
                    )
                    _uiState.update {
                        it.copy(
                            phrase = Phrase()
                        )
                    }
                }
            }

            is AddUIAction.SetPhraseToEdit -> {
                _uiState.update {
                    it.copy(
                        phrase = action.phrase,
                    )
                }
            }

            is AddUIAction.OnAboutProgramClick -> {
                _uiState.value.aboutProgramClickFun.invoke()
            }

            is AddUIAction.SetAboutProgramFun -> {
                _uiState.update {
                    it.copy(
                        aboutProgramClickFun = action.aboutProgramFun
                    )
                }
            }
        }
    }
}