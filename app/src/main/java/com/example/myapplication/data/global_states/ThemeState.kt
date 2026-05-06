package com.example.myapplication.data.global_states

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.myapplication.di.util.IOScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemeState @Inject constructor(
    private val dataStore: DataStore<@JvmSuppressWildcards Preferences>,
    @IOScope private val scope: CoroutineScope
) {
    companion object {
        private val THEME_MODE_KEY = stringPreferencesKey("theme_mode")
        private const val SYSTEM = "system"
        private const val LIGHT = "light"
        private const val DARK = "dark"
    }

    // null = follow system, false = force light, true = force dark
    private val _isDarkTheme = MutableStateFlow<Boolean?>(null)
    val isDarkTheme: StateFlow<Boolean?> = _isDarkTheme.asStateFlow()

    init {
        scope.launch {
            dataStore.data.collect { prefs ->
                _isDarkTheme.value = when (prefs[THEME_MODE_KEY]) {
                    LIGHT -> false
                    DARK -> true
                    else -> null
                }
            }
        }
    }

    fun setTheme(isDark: Boolean?) {
        scope.launch {
            dataStore.edit { prefs ->
                prefs[THEME_MODE_KEY] = when (isDark) {
                    true -> DARK
                    false -> LIGHT
                    null -> SYSTEM
                }
            }
        }
    }
}
