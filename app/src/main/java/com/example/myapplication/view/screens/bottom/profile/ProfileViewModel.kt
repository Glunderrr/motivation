package com.example.myapplication.view.screens.bottom.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.Phrase
import com.example.myapplication.data.global_states.GlobalSortedParameters
import com.example.myapplication.data.global_states.UserParametersState
import com.example.myapplication.usecases.phrase.ChangePhraseLikedStatus
import com.example.myapplication.usecases.phrase.DeletePhrases
import com.example.myapplication.usecases.phrase.GetAllPhrases
import com.example.myapplication.usecases.phrase.GetOwnPhrase
import com.example.myapplication.usecases.theme.GetTheme
import com.example.myapplication.usecases.utils.CopyPhraseToClipboard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val copyPhraseToClipboard: CopyPhraseToClipboard,
    private val getTheme: GetTheme,
    private val getAllPhrases: GetAllPhrases,
    private val deletePhrases: DeletePhrases,
    private val getOwnPhrase: GetOwnPhrase,
    private val changePhraseLikedStatus: ChangePhraseLikedStatus,
    private val sortParam: GlobalSortedParameters,
    private val userParam: UserParametersState
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfUIState())
    val uiState: StateFlow<ProfUIState> = _uiState

    init {
        viewModelScope.launch {
            combine(
                getTheme.invoke(),
                getAllPhrases.invoke(),
                getOwnPhrase.invoke()
            ) { themes, allPhraseList, getOwnPhrase ->
                _uiState.update { state ->
                    state.copy(
                        themes = themes,
                        allPhraseList = sortParam.sortList(allPhraseList),
                        ownList = sortParam.sortList(getOwnPhrase),
                    )
                }
            }.collect()
        }
    }

    fun onAction(action: ProfUIAction) {
        when (action) {
            is ProfUIAction.ClearPhraseSelectedList -> {
                clearSelectedList()
            }

            is ProfUIAction.DeleteSelectedPhrase -> {
                viewModelScope.launch {
                    deletePhrases.invoke(_uiState.value.selectedPhrases)
                }
                clearSelectedList()
            }

            is ProfUIAction.DoubleTapCard -> {
                viewModelScope.launch {
                    changePhraseLikedStatus.invoke(
                        action.phrase,
                        likeStatus = !action.phrase.isLiked
                    )
                }
            }

            is ProfUIAction.ChangeThemeStatusInSelectedList -> {
                with(sortParam) {
                    _uiState.update { state ->
                        if (themes.value.contains(action.theme)) {
                            remove(action.theme)
                        } else {
                            add(action.theme)
                        }

                        state.copy(
                            selectedThemes = themes.value,
                            allPhraseList = sortList(state.allPhraseList),
                            ownList = sortList(state.ownList)
                        )
                    }
                }
            }

            is ProfUIAction.LongPressCard -> {
                if (_uiState.value.selectedPhrases.isEmpty())
                    updateSelectedList(action.phrase)
            }

            is ProfUIAction.TapCard -> {
                if (_uiState.value.selectedPhrases.isNotEmpty())
                    updateSelectedList(action.phrase)
            }


            is ProfUIAction.ChangeIsAllPhrase -> {
                _uiState.update { state ->
                    state.copy(isAllPhrase = action.isAllPhrase)
                }
            }

            is ProfUIAction.CopyToClipBoard -> {
                copyPhraseToClipboard.invoke(action.phrase)
            }

            is ProfUIAction.SetNavigateFun -> {
                _uiState.update { state ->
                    state.copy(
                        navigateToAddScreen = action.navigateToAddScreen
                    )
                }
            }

            is ProfUIAction.NavigateToAddScreen -> {
                _uiState.value.navigateToAddScreen(
                    action.drawerElement,
                    action.phrase
                )
            }

            is ProfUIAction.SetPersonalData -> {
                userParam.updateFieldByKey(action.key, action.value)
            }

            ProfUIAction.SavePersonalData -> {
                userParam.savePersonalData()
            }
        }
    }

    private fun updateSelectedList(phrase: Phrase) {
        _uiState.update {
            if (it.selectedPhrases.contains(phrase))
                it.copy(
                    selectedPhrases = it.selectedPhrases - phrase
                )
            else
                it.copy(
                    selectedPhrases = it.selectedPhrases + phrase
                )
        }
    }

    private fun clearSelectedList() {
        _uiState.update {
            it.copy(
                selectedPhrases = emptyList()
            )
        }
    }
}