package com.example.coinview.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.coinview.R

@Composable
fun PriceText(modifier: Modifier = Modifier, value: Float, color: Color = MaterialTheme.colorScheme.onSurface ){
    Text(
        text = stringResource(id = R.string.price_value, value),
        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
        modifier = modifier,
        color = color
    )
}

@Composable
fun PercentText(modifier: Modifier = Modifier, value: Float){
    Text(
        text = stringResource(id = R.string.percent, value),
        color = if (value.toString()[0] == '-') Color.Red else Color.Green,
        fontSize = MaterialTheme.typography.bodySmall.fontSize,
        modifier = modifier
    )
}

@Composable
fun SymbolText( modifier: Modifier = Modifier, value: Float, symbol: String){
    Text(
        text = stringResource(id = R.string.symbol_value, value, symbol),
        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
        modifier = modifier,
    )
}
