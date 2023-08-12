package com.example.coinview.domain.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class Coins(
    @SerializedName("data")
    val listCoins: List<Coin>,
    val timestamp: Long
)

@Serializable
data class Coin(
    val id: String,
    val rank: String,
    val symbol: String,
    val name: String? = null,
    val priceUsd: String? = null,
    val marketCapUsd: String? = null,
    val supply: String? = null,
    val maxSupply: String? = null,
    val changePercent24Hr: String? = null,
    val volumeUsd24Hr: String? = null,
    val vwap24Hr: String? = null
)