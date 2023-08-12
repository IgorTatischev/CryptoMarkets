package com.example.coinview.domain.domain

import com.example.coinview.domain.model.Coins
import com.example.coinview.domain.model.Markets
import retrofit2.Response

interface CoinsRepository {

    suspend fun getCoins(page: Int, limit: Int) : Response<Coins>

    suspend fun getMarkets(id: String, page: Int, limit: Int) : Response<Markets>
}