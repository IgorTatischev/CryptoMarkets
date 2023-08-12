package com.example.coinview.presentation.screens.coins

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.coinview.domain.model.Coin
import com.example.coinview.domain.use_case.GetCoinsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class CoinsListViewModel(getCoinsUseCase: GetCoinsUseCase): ViewModel() {

    val coinsPage: Flow<PagingData<Coin>> = getCoinsUseCase().cachedIn(viewModelScope)

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: Event) {
        when(event){
            is Event.OnCoinClick -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.NavigateToDescription(event.item))
                }
            }

            is Event.OnSettingsClick -> {
                viewModelScope.launch { _eventFlow.emit(UiEvent.NavigateToSettings) }
            }
        }
    }
}