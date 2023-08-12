package com.example.coinview.presentation.screens.coin_description

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.coinview.R
import com.example.coinview.domain.model.Coin
import com.example.coinview.domain.model.Market
import com.example.coinview.presentation.components.AnimatedShimmer
import com.example.coinview.presentation.components.CoinCard
import com.example.coinview.presentation.components.ErrorScreen
import com.example.coinview.presentation.components.MarketItem
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun CoinDescriptionScreen(
    coin: Coin,
    coinId: String,
    viewModel: CoinDescriptionViewModel = koinViewModel(),
    navigator: DestinationsNavigator,
) {

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.NavigateToList -> {
                    navigator.popBackStack()
                }
            }
        }
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            coin.name?.let {
                DescriptionTopBar(
                    onClick = {
                        viewModel.onEvent(Event.OnBackClick)
                    },
                    name = it,
                    symbol = coin.symbol,
                    scrollBehavior
                )
            }
        },
    ) { padding ->
        val marketsList = viewModel.marketsPage.collectAsLazyPagingItems()
        Description(padding, marketsList, coin)
    }
}

@Composable
fun Description(padding: PaddingValues, marketsList: LazyPagingItems<Market>, coin: Coin) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(MaterialTheme.colorScheme.background)
    ) {

        when (val state = marketsList.loadState.refresh) {
            is LoadState.Error -> state.error.message?.let { ErrorScreen(it) }

            is LoadState.Loading -> {
                Column {
                    repeat(10) {
                        AnimatedShimmer()
                    }
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 15.dp)
                ) {
                    item {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            CoinCard(coin)
                            Text(
                                text = stringResource(R.string.market_title),
                                fontSize = MaterialTheme.typography.headlineMedium.fontSize
                            )
                        }
                    }

                    items(
                        count = marketsList.itemCount,
                        key = marketsList.itemKey { item -> item.uid },
                        contentType = marketsList.itemContentType { "Coin" }
                    ) { id: Int ->
                        val item = marketsList[id]
                        item?.let {
                            MarketItem(item)
                        }
                    }
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DescriptionTopBar(
    onClick: () -> Unit,
    name: String,
    symbol: String,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    val paddings = PaddingValues(horizontal = 10.dp, vertical = 10.dp)
    TopAppBar(
        title = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(paddings),
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {

                Row(Modifier.align(Alignment.CenterVertically)) {
                    Text(
                        text = name,
                        fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(Modifier.width(5.dp))
                    Text(
                        text = symbol,
                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Bottom),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        },
        navigationIcon = {
            FloatingActionButton(
                modifier = Modifier.padding(paddings),
                onClick = onClick,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        scrollBehavior = scrollBehavior
    )
}