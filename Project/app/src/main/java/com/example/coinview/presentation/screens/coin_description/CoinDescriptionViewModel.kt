package com.example.coinview.presentation.screens.coin_description

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.coinview.domain.model.Market
import com.example.coinview.domain.use_case.GetMarketsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class CoinDescriptionViewModel(getMarketsUseCase: GetMarketsUseCase, savedStateHandle: SavedStateHandle): ViewModel() {

    private var coinId = ""
    init {
        savedStateHandle.get<String>("coinId")?.let { coinId = it}
    }

    val marketsPage: Flow<PagingData<Market>> = getMarketsUseCase(coinId).cachedIn(viewModelScope)

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: Event) {
        when(event){
            is Event.OnBackClick -> {
                viewModelScope.launch { _eventFlow.emit(UiEvent.NavigateToList) }
            }
        }
    }
}