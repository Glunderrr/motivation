package com.example.myapplication.view.screens.bottom.favorite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.Phrase
import com.example.myapplication.data.global_states.GlobalSortedParameters
import com.example.myapplication.usecases.phrase.GetLikedPhrase
import com.example.myapplication.usecases.phrase.ChangePhraseLikedStatus
import com.example.myapplication.usecases.utils.CopyPhraseToClipboard
import com.example.myapplication.view.screens.bottom.add.DrawerElement
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavViewModel @Inject constructor(
    private val getLikedPhrase: GetLikedPhrase,
    private val changePhraseLikedStatus: ChangePhraseLikedStatus,
    private val copyPhraseToClipboard: CopyPhraseToClipboard,
    private val sortParam: GlobalSortedParameters
) : ViewModel() {
    private val _uiState = MutableStateFlow(FavUIState())
    val uiState: StateFlow<FavUIState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                getLikedPhrase.invoke(),
                sortParam.themes
            ) { phrases, themes ->
                _uiState.update { state ->
                    state.copy(
                        favList = sortParam.sortList(phrases),
                    )
                }
            }.collect()
        }
    }

    fun onAction(action: FavUIAction) {
        when (action) {
            is FavUIAction.LongPressCard -> {
                if (_uiState.value.selectedList.isEmpty())
                    updateSelectedList(action.phrase)
            }

            is FavUIAction.TapCard -> {
                if (_uiState.value.selectedList.isNotEmpty())
                    updateSelectedList(action.phrase)
            }

            is FavUIAction.ClearSelectedList -> {
                clearSelectedList()
            }

            is FavUIAction.DeleteSelectedPhrases -> {
                viewModelScope.launch {
                    changePhraseLikedStatus.invoke(
                        phrase = uiState.value.selectedList.toTypedArray(),
                        likeStatus = false
                    )
                    clearSelectedList()
                }
            }

            is FavUIAction.NavigateToAddScreen -> {
                _uiState.value.navigateToAddScreen(
                    action.drawerElement,
                    action.phrase
                )

            }

            is FavUIAction.SetNavigateFun -> {
                _uiState.update { state ->
                    state.copy(
                        navigateToAddScreen = action.navigateToAddScreen
                    )
                }
            }

            is FavUIAction.CopyPhrase -> {
                copyPhraseToClipboard.invoke(action.phrase)
            }
        }
    }

    private fun updateSelectedList(phrase: Phrase) {
        _uiState.update {
            if (it.selectedList.contains(phrase))
                it.copy(
                    selectedList = it.selectedList - phrase
                )
            else
                it.copy(
                    selectedList = it.selectedList + phrase
                )
        }
    }

    private fun clearSelectedList() {
        _uiState.update { state ->
            state.copy(
                selectedList = emptyList()
            )
        }
    }
}
