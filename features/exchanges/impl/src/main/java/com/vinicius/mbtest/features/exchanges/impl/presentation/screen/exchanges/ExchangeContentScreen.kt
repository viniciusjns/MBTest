package com.vinicius.mbtest.features.exchanges.impl.presentation.screen.exchanges

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vinicius.mbtest.features.exchanges.impl.presentation.state.ExchangesViewState

@Composable
fun ExchangeContentScreen(
    viewState: ExchangesViewState,
    navController: NavHostController
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(viewState.exchanges, key = { it.exchangeId!! }) { exchange ->
            ExchangeItem(exchange, navController)
        }
    }
}