package com.example.coinview.presentation.screens.coin_description

sealed class Event {
    object OnBackClick: Event()
}

sealed class UiEvent{
    object NavigateToList: UiEvent()

}