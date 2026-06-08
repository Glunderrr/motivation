package com.example.myapplication.view.screens.bottom.profile

import android.util.Log
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
        // Підписується одночасно на теми, всі фрази та власні фрази;
        // об'єднує потоки через combine для синхронного оновлення UI
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

    // Обробляє всі UI-події екрана профілю відповідно до патерну UDF
    fun onAction(action: ProfUIAction) {
        when (action) {
            is ProfUIAction.ClearPhraseSelectedList -> {
                clearSelectedList()
            }

            is ProfUIAction.DeleteSelectedPhrase -> {
                // Видаляє вибрані фрази з бази даних та скидає список вибору
                viewModelScope.launch {
                    deletePhrases.invoke(_uiState.value.selectedPhrases)
                }
                clearSelectedList()
            }

            is ProfUIAction.DoubleTapCard -> {
                // Перемикає статус "улюблена" для фрази при подвійному натисканні
                viewModelScope.launch {
                    changePhraseLikedStatus.invoke(
                        action.phrase,
                        likeStatus = !action.phrase.isLiked
                    )
                }
            }

            is ProfUIAction.ChangeThemeStatusInSelectedList -> {
                // Додає або видаляє тему з фільтрів сортування та перераховує відсортовані списки
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
                // Довге натискання активує режим вибору лише якщо список вибраних порожній
                if (_uiState.value.selectedPhrases.isEmpty())
                    updateSelectedList(action.phrase)
            }

            is ProfUIAction.TapCard -> {
                // Звичайне натискання додає/прибирає фразу лише в активному режимі вибору
                if (_uiState.value.selectedPhrases.isNotEmpty())
                    updateSelectedList(action.phrase)
            }

            is ProfUIAction.ChangeIsAllPhrase -> {
                // Перемикає відображення між усіма фразами та власними
                _uiState.update { state ->
                    state.copy(isAllPhrase = action.isAllPhrase)
                }
            }

            is ProfUIAction.CopyToClipBoard -> {
                copyPhraseToClipboard.invoke(action.phrase)
            }

            is ProfUIAction.SetNavigateFun -> {
                // Зберігає лямбду навігації, передану ззовні через NavDisplay
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
                // Оновлює окреме поле профілю користувача за ключем без перестворення всього об'єкта
                userParam.updateFieldByKey(action.key, action.value).apply {
                    Log.d(
                        "ProfileViewModel",
                        "SetPersonalData: key=${action.key}, value=${action.value}, personal=${userParam.personalState.value}"
                    )
                }
            }

            ProfUIAction.SavePersonalData -> {
                // Зберігає оновлений профіль користувача в базу даних
                userParam.savePersonalData()
            }
        }
    }

    // Додає фразу до списку вибраних або видаляє її, якщо вона вже там є
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

    // Очищає список вибраних фраз та завершує режим групового вибору
    private fun clearSelectedList() {
        _uiState.update {
            it.copy(
                selectedPhrases = emptyList()
            )
        }
    }
}
