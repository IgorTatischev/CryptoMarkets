package com.example.coinview.presentation.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinview.util.ThemeDataStore
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(private val themeDataStore: ThemeDataStore): ViewModel() {

    fun setTheme(dark: Boolean){
        viewModelScope.launch {
            themeDataStore.saveTheme(dark)
        }
    }

    val readTheme: StateFlow<Boolean> = themeDataStore.getTheme.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )
}