package com.example.coinview.presentation.screens.coins

import com.example.coinview.domain.model.Coin

sealed class Event {
    data class OnCoinClick(val item: Coin? = null): Event()
    object OnSettingsClick: Event()
}

sealed class UiEvent{
    object NavigateToSettings: UiEvent()
    data class NavigateToDescription(val coinItem: Coin? = null): UiEvent()
}