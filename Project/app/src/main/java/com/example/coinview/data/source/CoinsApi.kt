package com.example.coinview.data.source

import com.example.coinview.domain.model.Coins
import com.example.coinview.domain.model.Markets
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinsApi {
    @GET("assets")
    suspend fun getCoins(
        @Query("limit") limit: Int,
        @Query("offset") page: Int
    ): Response<Coins>

    @GET("markets")
    suspend fun getMarkets(
        @Query("baseId") id: String,
        @Query("limit") limit: Int,
        @Query("offset") page: Int
    ): Response<Markets>
}