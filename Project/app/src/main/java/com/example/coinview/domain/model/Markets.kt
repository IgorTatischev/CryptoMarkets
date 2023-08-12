package com.example.coinview.domain.model

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class Markets(
    @SerializedName("data")
    val listMarkets: List<Market>,
    val timestamp: Long
)

data class Market(
    val uid: String = UUID.randomUUID().toString(),
    val exchangeId: String,
    val rank: String,
    val baseId: String,
    val baseSymbol: String,
    val quoteId: String,
    val quoteSymbol: String,
    val priceQuote: String,
    val priceUsd: String?,
    val percentExchangeVolume: String?,
    val volumeUsd24Hr: String?,
    val tradesCount24Hr: String?
)