package com.example.coinview.domain.use_case

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.coinview.data.paging.CoinsPageSource
import com.example.coinview.domain.domain.CoinsRepository
import com.example.coinview.domain.model.Coin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetCoinsUseCase(private val repository: CoinsRepository) {
    operator fun invoke(): Flow<PagingData<Coin>> = Pager(
        PagingConfig(pageSize = 100, initialLoadSize = 100)
    ){
        CoinsPageSource(repository = repository)
    }.flow.flowOn(Dispatchers.IO)
}

