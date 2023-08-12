package com.example.coinview.presentation.screens.coins

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.MoreHoriz
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
import com.example.coinview.presentation.components.AnimatedShimmer
import com.example.coinview.presentation.components.CoinItem
import com.example.coinview.presentation.components.ErrorScreen
import com.example.coinview.presentation.screens.destinations.CoinDescriptionScreenDestination
import com.example.coinview.presentation.screens.destinations.SettingsScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun CoinsListScreen(
    viewModel: CoinsListViewModel = koinViewModel(),
    navigator: DestinationsNavigator,
) {

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.NavigateToSettings -> {
                    navigator.navigate(SettingsScreenDestination)
                }

                is UiEvent.NavigateToDescription -> {
                    val item = event.coinItem
                    item?.let {
                        navigator.navigate(CoinDescriptionScreenDestination(item, item.id))
                    }
                }
            }
        }
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CoinsTopBar(
                onClick = {
                    viewModel.onEvent(Event.OnSettingsClick)
                },
                scrollBehavior = scrollBehavior
            )
        },
    ) { padding ->
        val coinsList = viewModel.coinsPage.collectAsLazyPagingItems()
        CoinsList(padding = padding, coinsList = coinsList)
    }
}

@Composable
fun CoinsList(
    padding: PaddingValues,
    coinsList: LazyPagingItems<Coin>,
    viewModel: CoinsListViewModel = koinViewModel(),
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(MaterialTheme.colorScheme.background)
    ) {

        when (val state = coinsList.loadState.refresh) {
            is LoadState.Error -> state.error.message?.let { ErrorScreen(it) }

            is LoadState.Loading -> {
                Column {
                    repeat(10) {
                        AnimatedShimmer()
                    }
                }
            }

            else -> {
                LazyColumn {
                    items(
                        count = coinsList.itemCount,
                        key = coinsList.itemKey { item -> item.rank },
                        contentType = coinsList.itemContentType { "Coin" }
                    ) { id: Int ->
                        val item = coinsList[id]
                        if (item != null) {
                            CoinItem(item) {
                                viewModel.onEvent(Event.OnCoinClick(item))
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinsTopBar(onClick: () -> Unit, scrollBehavior: TopAppBarScrollBehavior) {

    val paddings = PaddingValues(horizontal = 10.dp, vertical = 10.dp)

    TopAppBar(
        title = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(paddings)
            ) {
                Text(
                    text = stringResource(R.string.app_name),
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.width(5.dp))
                Text(
                    text = stringResource(R.string.version),
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Bottom),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        scrollBehavior = scrollBehavior,
        actions = {
            FloatingActionButton(
                modifier = Modifier.padding(paddings),
                onClick = onClick,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ) {
                Icon(
                    imageVector = Icons.Default.MoreHoriz,
                    contentDescription = "Settings"
                )
            }
        }
    )
}



