package com.example.coinview.presentation.components

import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.coinview.R
import com.example.coinview.domain.model.Market
import com.example.coinview.presentation.ui.onSurfaceColor

@Composable
fun MarketItem(item: Market) {
    if (item.priceUsd != null && item.volumeUsd24Hr != null && item.percentExchangeVolume != null && item.tradesCount24Hr != null) {

        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(4.dp),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.padding(horizontal = 5.dp, vertical = 5.dp)
        ) {

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = item.exchangeId,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        modifier = Modifier.padding(start = 10.dp, top = 15.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = stringResource(id = R.string.volume),
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        modifier = Modifier.padding(start = 10.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = stringResource(id = R.string.trades),
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        modifier = Modifier.padding(start = 10.dp, bottom = 15.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Text(
                    text = stringResource(id = R.string.symbols, item.baseSymbol, item.quoteSymbol),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .weight(0.6f)
                        .align(Alignment.Top)
                        .clip(RoundedCornerShape(bottomStart = 5.dp, bottomEnd = 5.dp))
                        .background(onSurfaceColor)
                        .padding(2.dp)
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    PriceText(
                        value = item.priceUsd.toFloat(),
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(end = 15.dp, top = 15.dp)
                    )
                    PriceText(
                        value = item.volumeUsd24Hr.toFloat(),
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(end = 15.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row(
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(end = 15.dp, bottom = 15.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        PercentText(
                            value = item.percentExchangeVolume.toFloat(),
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                        Text(text = item.tradesCount24Hr, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
}