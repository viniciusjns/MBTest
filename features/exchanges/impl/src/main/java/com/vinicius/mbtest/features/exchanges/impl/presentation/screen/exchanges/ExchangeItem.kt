package com.vinicius.mbtest.features.exchanges.impl.presentation.screen.exchanges

import ExchangesViewIntent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.vinicius.mbtest.features.exchanges.impl.R
import com.vinicius.mbtest.features.exchanges.impl.presentation.model.ExchangeDataUi

const val EXCHANGES_ITEM_TEST_TAG = "EXCHANGES_ITEM_TEST_TAG"

@Composable
fun ExchangeItem(
    exchangeDataUi: ExchangeDataUi,
    viewIntent: (ExchangesViewIntent) -> Unit
) {
    Card(
        modifier = Modifier
            .testTag("${EXCHANGES_ITEM_TEST_TAG}_${exchangeDataUi.exchangeId}")
            .padding(bottom = 8.dp)
            .clickable {
                viewIntent(ExchangesViewIntent.OnExchangeClicked(exchangeId = exchangeDataUi.exchangeId!!))
            },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.secondary
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 1,
                        text = exchangeDataUi.name!!,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 1,
                        text = stringResource(R.string.id, exchangeDataUi.exchangeId!!),
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.labelSmall,
                    )
                }
            }
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = stringResource(R.string.usd_volume),
                style = MaterialTheme.typography.labelLarge
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.one_hour),
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .background(
                            shape = MaterialTheme.shapes.small,
                            color = MaterialTheme.colorScheme.primary
                        )
                        .padding(horizontal = 8.dp),
                    text = exchangeDataUi.volume1hrsUsd.toString(),
                    style = MaterialTheme.typography.labelSmall,
                    color = if (isSystemInDarkTheme()) Color.DarkGray else Color.White
                )
            }
        }
    }
}