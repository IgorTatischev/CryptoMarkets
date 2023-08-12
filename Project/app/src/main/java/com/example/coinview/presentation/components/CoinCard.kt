package com.example.coinview.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.coinview.R
import com.example.coinview.domain.model.Coin

@Composable
fun CoinCard(item: Coin) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.padding(vertical = 10.dp)
    ) {
        Row(
            Modifier
                .padding(15.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ColumLabels()
            ColumnValues(item = item)
        }
    }
}

@Composable
fun ColumLabels(){
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            text = stringResource(id = R.string.price),
            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = stringResource(id = R.string.vwap),
            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = stringResource(id = R.string.volume),
            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = stringResource(id = R.string.market),
            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = stringResource(id = R.string.supply),
            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = stringResource(id = R.string.max_supply),
            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun ColumnValues(item: Coin){
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
            modifier = Modifier.align(Alignment.End),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item.changePercent24Hr?.let {
                PercentText(
                    value = it.toFloat(),
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
            item.priceUsd?.let { PriceText(value = it.toFloat()) }
        }
        item.vwap24Hr?.let {
            PriceText(
                value = it.toFloat(),
                modifier = Modifier.align(Alignment.End)
            )
        }
        item.volumeUsd24Hr?.let {
            PriceText(
                value = it.toFloat(),
                modifier = Modifier.align(Alignment.End)
            )
        }
        item.marketCapUsd?.let {
            PriceText(
                value = it.toFloat(),
                modifier = Modifier.align(Alignment.End)
            )
        }
        item.supply?.let {
            SymbolText(
                value = it.toFloat(),
                symbol = item.symbol,
                modifier = Modifier.align(Alignment.End)
            )
        }
        item.maxSupply?.let {
            SymbolText(
                value = it.toFloat(),
                symbol = item.symbol,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}