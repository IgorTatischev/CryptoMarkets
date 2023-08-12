package com.example.coinview.domain.use_case

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.coinview.data.paging.MarketsPageSource
import com.example.coinview.domain.domain.CoinsRepository
import com.example.coinview.domain.model.Market
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetMarketsUseCase(private val repository: CoinsRepository) {
    operator fun invoke(id: String): Flow<PagingData<Market>> = Pager(
        PagingConfig(pageSize = 10, initialLoadSize = 10)
    ){
        MarketsPageSource(repository = repository, id = id)
    }.flow.flowOn(Dispatchers.IO)
}