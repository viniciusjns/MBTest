package com.vinicius.mbtest.features.exchanges.impl.presentation.screen.exchangeDetail

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vinicius.mbtest.features.exchanges.impl.R
import com.vinicius.mbtest.features.exchanges.impl.presentation.model.ExchangeDataUi
import com.vinicius.mbtest.features.exchanges.impl.presentation.viewModel.ExchangeDetailViewIntent

const val BACK_TEST_TAG = "BACK_TEST_TAG"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExchangeDetailContentScreen(
    exchange: ExchangeDataUi,
    intent: (ExchangeDetailViewIntent) -> Unit,
    scrollState: ScrollState = rememberScrollState()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        modifier = Modifier.testTag(BACK_TEST_TAG),
                        onClick = { intent(ExchangeDetailViewIntent.OnBackPressed) }
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrollState)
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    Text(
                        text = exchange.name ?: stringResource(R.string.not_available),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = exchange.website.orEmpty(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Text(
                text = stringResource(R.string.available_symbols, exchange.dataSymbolsCount ?: "0"),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(16.dp))

            SectionCard(title = stringResource(R.string.quote_periods)) {
                InfoRow(stringResource(R.string.start), exchange.dataQuoteStart ?: stringResource(R.string.not_available))
                InfoRow(stringResource(R.string.end), exchange.dataQuoteEnd ?: stringResource(R.string.not_available))
            }

            SectionCard(title = stringResource(R.string.order_book)) {
                InfoRow(stringResource(R.string.start), exchange.dataOrderBookStart ?: stringResource(R.string.not_available))
                InfoRow(stringResource(R.string.end), exchange.dataOrderBookEnd ?: stringResource(R.string.not_available))
            }

            SectionCard(title = stringResource(R.string.trades)) {
                InfoRow(stringResource(R.string.start), exchange.dataTradeStart ?: stringResource(R.string.not_available))
                InfoRow(stringResource(R.string.end), exchange.dataTradeEnd ?: stringResource(R.string.not_available))
            }

            SectionCard(title = stringResource(R.string.transaction_volumes)) {
                VolumeRow(stringResource(R.string.last_hour), exchange.volume1hrsUsd ?: "0")
                HorizontalDivider()
                VolumeRow(stringResource(R.string.last_day), exchange.volume1dayUsd ?: "0")
                HorizontalDivider()
                VolumeRow(stringResource(R.string.last_month), exchange.volume1mthUsd ?: "0")
            }
        }
    }

}

@Composable
fun SectionCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.secondary
            ),
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                content()
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.bodyMedium)
        Text(value, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun VolumeRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
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
        website = "https://www.mercadobitcoin.com.br/",
        name = "Mercado Bitcoin",
        dataQuoteStart = "15/03/17 00:00",
        dataQuoteEnd = "24/08/24 00:00",
        dataOrderBookStart = "14/03/17 20:05",
        dataOrderBookEnd = "06/07/23 00:00",
        dataTradeStart = "07/03/17 00:00",
        dataTradeEnd = "24/08/24 00:00",
        dataSymbolsCount = "462",
        volume1hrsUsd = "228.67",
        volume1dayUsd = "829.7K",
        volume1mthUsd = "192.7M"
    )
    ExchangeDetailContentScreen(exchange = mockExchange, intent = {})
}