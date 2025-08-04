package com.example.myapplication.data.global_states

import com.example.myapplication.data.model.Phrase
import com.example.myapplication.data.model.Theme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlobalSortedParameters @Inject constructor() {
    private val _themes = MutableStateFlow<List<Theme>>(emptyList())
    val themes: StateFlow<List<Theme>> = _themes

    private val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

    fun add(theme: Theme) {
        _themes.value = listOf(theme) + _themes.value
    }

    fun remove(theme: Theme) {
        _themes.value = _themes.value - listOf(theme)
    }

    fun getPriorityMap(): Map<String, Int> {
        return _themes.value.mapIndexed { index, theme ->
            theme.themeName to index
        }.toMap()
    }

    fun sortList(list: List<Phrase>) =
        list.sortedWith(
            compareBy(
                { phrase -> getPriorityMap()[phrase.theme] ?: Int.MAX_VALUE },
                { phrase -> -(dateFormat.parse(phrase.date)?.time ?: Long.MAX_VALUE) }
            )
        )
}