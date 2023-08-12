package com.example.coinview.data.repository

import com.example.coinview.data.source.CoinsApi
import com.example.coinview.domain.domain.CoinsRepository
import com.example.coinview.domain.model.Coins
import com.example.coinview.domain.model.Markets
import retrofit2.Response

class CoinRepositoryImpl(private val api: CoinsApi): CoinsRepository {
    override suspend fun getCoins(page: Int, limit: Int): Response<Coins> {
       return api.getCoins(page = page, limit = limit)
    }

    override suspend fun getMarkets(id: String, page: Int, limit: Int): Response<Markets> {
        return api.getMarkets(id = id, page = page, limit = limit)
    }
}