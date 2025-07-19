package com.vinicius.mbtest.features.exchanges.impl.presentation.screen.exchanges

import ExchangesViewIntent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.vinicius.mbtest.features.exchanges.impl.presentation.state.ExchangesViewState

const val LIST_TEST_TAG = "LIST_TEST_TAG"

@Composable
fun ExchangeContentScreen(
    viewState: ExchangesViewState,
    viewIntent: (ExchangesViewIntent) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .testTag(LIST_TEST_TAG),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(viewState.exchanges, key = { it.exchangeId!! }) { exchange ->
            ExchangeItem(exchange, viewIntent)
        }
    }
}