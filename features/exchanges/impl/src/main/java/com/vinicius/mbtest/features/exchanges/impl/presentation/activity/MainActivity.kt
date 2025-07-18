package com.vinicius.mbtest.features.exchanges.impl.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.vinicius.mbtest.core.ui.theme.MBTestTheme
import com.vinicius.mbtest.features.exchanges.impl.navigation.ScreenRoutes
import com.vinicius.mbtest.features.exchanges.impl.navigation.SetupNavigation

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MBTestTheme(darkTheme = isSystemInDarkTheme()) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    SetupNavigation(
                        navController = navController,
                        startDestination = ScreenRoutes.ExchangeScreenRoute.route
                    )
                }
            }
        }
    }
}
