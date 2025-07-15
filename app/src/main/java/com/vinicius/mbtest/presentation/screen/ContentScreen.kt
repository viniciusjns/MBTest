package com.vinicius.mbtest.presentation.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vinicius.mbtest.presentation.state.ExchangesViewState

@Composable
fun ContentScreen(viewState: ExchangesViewState) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(viewState.exchanges, key = { it.exchangeId!! }) { exchange ->
            ExchangeItem(exchange)
        }
    }
}