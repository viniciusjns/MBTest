package com.vinicius.mbtest.presentation.screen.exchangeDetail

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vinicius.mbtest.core.ErrorScreen
import com.vinicius.mbtest.presentation.model.ExchangeDataUi
import com.vinicius.mbtest.presentation.viewModel.ExchangeDetailViewIntent
import com.vinicius.mbtest.presentation.viewModel.ExchangeDetailViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ExchangeDetailScreen(exchangeId: String) {
    val viewModel: ExchangeDetailViewModel = koinViewModel()
    val viewState = viewModel.state.collectAsState()

    LaunchedEffect(exchangeId) {
        viewModel.dispatchViewIntent(ExchangeDetailViewIntent.GetExchangeById(exchangeId))
    }

    viewState.value.exchange?.let {
        ExchangeDetailContent(
            exchange = it
        )
    } ?: run {
        ErrorScreen(
            errorMessage = "Exchange not found",
            errorButtonText = "Go back"
        )
    }
}

@Composable
fun ExchangeDetailContent(
    exchange: ExchangeDataUi,
    scrollState: ScrollState = rememberScrollState()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Image(
//            painter = rememberAsyncImagePainter(exchange.iconUrl),
//            contentDescription = "Exchange Icon",
//            contentScale = ContentScale.Fit,
//            modifier = Modifier
//                .size(96.dp)
//                .padding(8.dp)
//        )

        Text(
            text = exchange.name!!,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Text(
            text = exchange.website!!,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        InfoSection("Períodos de Cotação", exchange.dataQuoteStart!!, exchange.dataQuoteEnd!!)
        InfoSection("Períodos de Livro de Ofertas", exchange.dataOrderBookStart!!, exchange.dataOrderBookEnd!!)
        InfoSection("Períodos de Negociação", exchange.dataTradeStart!!, exchange.dataTradeEnd!!)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Volumes de Transação",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.fillMaxWidth()
        )

        VolumeRow("Última hora", exchange.volume1hrsUsd!!)
        VolumeRow("Último dia", exchange.volume1dayUsd!!)
        VolumeRow("Último mês", exchange.volume1mthUsd!!)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Símbolos disponíveis: ${exchange.dataSymbolsCount}",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun InfoSection(title: String, start: String, end: String) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(
            text = "$title:",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Medium
        )
        Text(text = "Início: $start", style = MaterialTheme.typography.bodySmall)
        Text(text = "Fim: $end", style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun VolumeRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        Text(text = value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
    }
}

@Composable
@Preview(showBackground = true)
fun ExchangeDetailScreenPreview() {
    val mockExchange = ExchangeDataUi(
        exchangeId = "1",
        name = "Mock Exchange",
        website = "https://mockexchange.com",
        dataQuoteStart = "2020-01-01",
        dataQuoteEnd = "2024-01-01",
        dataOrderBookStart = "2020-01-01",
        dataOrderBookEnd = "2024-01-01",
        dataTradeStart = "2020-01-01",
        dataTradeEnd = "2024-01-01",
        dataSymbolsCount = "100",
        volume1hrsUsd = "5000.00",
        volume1dayUsd = "120000.00",
        volume1mthUsd = "3000000.00"
    )

    ExchangeDetailContent(exchange = mockExchange)
}
