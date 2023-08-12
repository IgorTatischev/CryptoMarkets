package com.example.coinview.presentation.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.coinview.R
import com.example.coinview.presentation.ui.surfaceColor
import com.example.coinview.presentation.ui.onSurfaceColor
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = koinViewModel(),
    navigator: DestinationsNavigator,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SettingsTopBar(
                onClick = {
                    navigator.popBackStack()
                },
                scrollBehavior = scrollBehavior
            )
        },
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(4.dp),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(15.dp), Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.dark_theme),
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        modifier = Modifier.align(Alignment.CenterVertically),
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    val theme = viewModel.readTheme.collectAsStateWithLifecycle().value
                    Switch(
                        checked = theme,
                        onCheckedChange = {
                            viewModel.setTheme(!theme)
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = surfaceColor,
                            checkedTrackColor = onSurfaceColor
                        )
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsTopBar(
    onClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    val paddings = PaddingValues(horizontal = 10.dp, vertical = 10.dp)
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.settings),
                fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                color = MaterialTheme.colorScheme.onSurface
            )
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