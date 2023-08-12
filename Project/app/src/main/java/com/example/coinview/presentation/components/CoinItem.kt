package com.example.coinview.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.coinview.domain.model.Coin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinItem(item: Coin, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp)
    ) {
        Row(
            Modifier
                .padding(15.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                item.name?.let { name ->
                    Text(
                        name,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Text(
                    item.symbol,
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                item.priceUsd?.let {
                    PriceText(value = it.toFloat(), modifier = Modifier.align(Alignment.End))
                }
                item.changePercent24Hr?.let {
                    PercentText(value = it.toFloat(), modifier = Modifier.align(Alignment.End))
                }
            }
        }
    }
}