package com.example.coinview.presentation.main_activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import com.example.coinview.presentation.screens.NavGraphs
import com.example.coinview.presentation.screens.settings.SettingsViewModel
import com.example.coinview.presentation.ui.CoinsViewTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import org.koin.androidx.compose.koinViewModel


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val settingsViewModel = koinViewModel<SettingsViewModel>()
            val isDarkTheme = settingsViewModel.readTheme.collectAsState().value

            CoinsViewTheme(darkTheme = isDarkTheme) {
                Surface(color = MaterialTheme.colorScheme.background) {
                    DestinationsNavHost(navGraph = NavGraphs.root)
                }
            }
        }
    }
}

