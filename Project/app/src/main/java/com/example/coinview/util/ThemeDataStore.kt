package com.example.coinview.util

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("score")

class ThemeDataStore(context: Context) {

    private val themeDataStore = context.dataStore

    companion object {
        val THEME_KEY = booleanPreferencesKey("theme")
    }

    val getTheme: Flow<Boolean> = themeDataStore.data.map { preferences ->
        preferences[THEME_KEY] ?: false
    }

    suspend fun saveTheme(isDark: Boolean) {
        themeDataStore.edit { preferences ->
            preferences[THEME_KEY] = isDark
        }
    }
}